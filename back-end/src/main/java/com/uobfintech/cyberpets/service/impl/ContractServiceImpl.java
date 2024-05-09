package com.uobfintech.cyberpets.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.time.ZoneId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.uobfintech.cyberpets.abi.PetAuction_abi;
import com.uobfintech.cyberpets.abi.PetLottery_abi;
import com.uobfintech.cyberpets.abi.PetMarket_abi;
import com.uobfintech.cyberpets.abi.PetNFT_abi;
import com.uobfintech.cyberpets.entity.Auction;
import com.uobfintech.cyberpets.entity.History;
import com.uobfintech.cyberpets.entity.HistoyDTO;
import com.uobfintech.cyberpets.entity.LotteryHistory;
import com.uobfintech.cyberpets.entity.Pet;
import com.uobfintech.cyberpets.repository.MongoDAO;
import com.uobfintech.cyberpets.service.ContractService;

import static com.uobfintech.cyberpets.abi.PetAuction_abi.*;
import static com.uobfintech.cyberpets.abi.PetLottery_abi.*;
import static com.uobfintech.cyberpets.abi.PetMarket_abi.*;
import static com.uobfintech.cyberpets.abi.PetNFT_abi.*;

@Slf4j
@Service
@EnableAsync
public class ContractServiceImpl implements ContractService {
    private final Web3j web3j;
    private final String contractAddressLottery;
    private final String contractAddressAuction;
    private final String contractAddressMarket;

    private final String contractAddressNFT;

    private List<Auction> auctionList;

    private Map<Integer, History> historyMap =  new HashMap<>();

    // @Autowired
    private MongoDAO mongoDAO;

    @Autowired
    private TaskScheduler taskScheduler;

    // Assume a very low gas price since it's a read operation.

    private final static BigInteger WEI_IN_ETHER = new BigInteger("1000000000000000000"); // 10^18


    public ContractServiceImpl(Web3j web3j, String contractAddressLottery, String contractAddressAuction, String contractAddressMarket, String contractAddressNFT, MongoDAO mongoDAO) {
        this.web3j = web3j;
        this.contractAddressLottery = contractAddressLottery;
        this.contractAddressAuction = contractAddressAuction;
        this.contractAddressMarket = contractAddressMarket;
        this.contractAddressNFT = contractAddressNFT;
        this.auctionList = new ArrayList<>();
        this.mongoDAO = mongoDAO;
    }

    private String getAddressFromTopic(String topicHex) {
        // 地址是从第25个字符开始的，持续到字符串结束（去除前面的24个字符，即12个字节的零）
        return "0x" + topicHex.substring(24);
    }

    /**
     * 解码 data 字符串中的 uint256 数据。
     * 注意：此函数只是一个简单示例，实际解码可能需要考虑数据的偏移量和具体格式。
     */
    private BigInteger decodeUint256(String data) {
        // 假设 data 就是直接的数值字符串，实际中可能需要根据ABI规则进行解码
        return new BigInteger(data.substring(2), 16); // 去除前缀'0x'，假设数据是十六进制的
    }

    public LocalDateTime convertBlockchainTimestamp(BigInteger timestamp) {
        // 将秒转换为毫秒
        long milliseconds = timestamp.multiply(BigInteger.valueOf(1000)).longValue();

        // 创建Instant对象
        Instant instant = Instant.ofEpochMilli(milliseconds);

        // 转换为LocalDateTime（这里假设系统默认时区）

        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    public ZonedDateTime convertToZonedDateTimeUTCPlusOne(BigInteger timestamp) {
        // 将秒转换为毫秒
        long milliseconds = timestamp.multiply(BigInteger.valueOf(1000)).longValue();

        // 创建Instant对象
        Instant instant = Instant.ofEpochMilli(milliseconds);

        // 转换为ZonedDateTime，在UTC+1时区

        return instant.atZone(ZoneId.of("Europe/London"));
    }

    public BigInteger convertBackToTimestamp(ZonedDateTime zonedDateTime) {
        // 从ZonedDateTime获取Instant对象
        Instant instant = zonedDateTime.toInstant();

        // 将Instant的毫秒数转换为秒数
        long seconds = instant.getEpochSecond();

        // 将秒数转换为BigInteger
        return BigInteger.valueOf(seconds);
    }

    @Async
    public void callAuctionEndedFunc(BigInteger tokenId) {
        try {
            Credentials credentials = Credentials.create("b67d61efaab1a9d65a53221c8cfbd89d38c72d6ff0ed40b8e4e4a56af607cb0b");
            PetAuction_abi petAuctionAbi = new PetAuction_abi(contractAddressAuction, web3j, credentials, new DefaultGasProvider());
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = petAuctionAbi.endAuction(tokenId);

            try {
                TransactionReceipt result = remoteFunctionCall.send();
                System.out.println("Transaction complete, block number: " + result.getBlockNumber());

                System.out.println("Contract returned: " + result);
                System.out.println("Transaction complete, block number: " + result.getBlockNumber());


                String transactionHash = result.getTransactionHash();
                EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(transactionHash).send();
                if (receipt.getTransactionReceipt().isPresent()) {
                    System.out.println("Transaction " + transactionHash + " was included in block " + receipt.getTransactionReceipt().get().getBlockNumber());
                } else {
                    System.out.println("Transaction is still pending...");
                }
            } catch (Exception e) {
                System.err.println("Transaction failed: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error during contract function call: " + e.getMessage());
        }
    }


//    @Async
//    @Scheduled(fixedRate = 600000) // 每600000毫秒（即十分钟）执行一次
//    public void callContractFunctionPeriodically() {
//        try {
//            Credentials credentials = Credentials.create("b67d61efaab1a9d65a53221c8cfbd89d38c72d6ff0ed40b8e4e4a56af607cb0b");
////            BigInteger GAS_LIMIT = BigInteger.valueOf(9000000L);
////            BigInteger GAS_PRICE = BigInteger.valueOf(2100000000L);
//            PetAuction_abi petAuctionAbi = new PetAuction_abi(contractAddressAuction, web3j, credentials, new DefaultGasProvider());
//            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall
//                    = petAuctionAbi.checkAndEndAuctions();
//            // TransactionReceipt result = remoteFunctionCall.send();
//
//            try {
//                TransactionReceipt result = remoteFunctionCall.send();
//                System.out.println("Transaction complete, block number: " + result.getBlockNumber());
//
//                System.out.println("Contract returned: " + result);
//                System.out.println("Transaction complete, block number: " + result.getBlockNumber());
//
//
//                String transactionHash = result.getTransactionHash();
//                EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(transactionHash).send();
//                if (receipt.getTransactionReceipt().isPresent()) {
//                    System.out.println("Transaction " + transactionHash + " was included in block " + receipt.getTransactionReceipt().get().getBlockNumber());
//                } else {
//                    System.out.println("Transaction is still pending...");
//                }
//            } catch (Exception e) {
//                System.err.println("Transaction failed: " + e.getMessage());
//                e.printStackTrace();
//            }
//
////            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
////            Transaction transaction = Transaction.createEthCallTransaction(null, contractAddressAuction, encodedFunction);
////            EthCall response = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
////
////            if (response.hasError()) {
////                System.out.println("Error: " + response.getError().getMessage());
////            }
////            List<Type> result = remoteFunctionCall.decodeFunctionResponse(response.getValue());
////
////            if (!result.isEmpty()){
////                System.out.println(result.get(0).getValue().toString());
////            }else {
////                System.out.println("No result");
////            }
//
//
//
////            String functionName = "checkAndEndAuctions";
////            // 调用具体的合约函数
////            String result = callContractFunction(functionName);
////            System.out.println("Contract function result: " + result);
//
//        } catch (Exception e) {
//            System.err.println("Error during contract function call: " + e.getMessage());
//        }
//    }


    public String callContractFunction(String functionName) throws Exception {
        Function function = new Function(
                functionName,
                java.util.Collections.emptyList(),
                // Arrays.asList(new Uint256(BigInteger.valueOf(123))),  // Your input parameters
                Collections.singletonList(new org.web3j.abi.TypeReference<Uint256>() {}));

        String encodedFunction = FunctionEncoder.encode(function);

        // Default gas price and limit, adjust according to your contract's needs
        BigInteger gasPrice = BigInteger.valueOf(0); // Usually you would use a non-zero gas price
        BigInteger gasLimit = BigInteger.valueOf(4_700_000); // Default gas limit for Ethereum transactions

        Transaction transaction = Transaction.createEthCallTransaction(null, contractAddressAuction, encodedFunction);
        EthCall response = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

        if (response.hasError()) {
            return "Error: " + response.getError().getMessage();
        }

        List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        return !result.isEmpty() ? result.get(0).getValue().toString() : "No result";
    }


    public String callContractFunction(String functionName, List<Type> inputParameters, List<TypeReference<?>> outputParameters) throws Exception {
        Function function = new Function(
                functionName,
                inputParameters,
                outputParameters);

        // 编码函数调用，包含输入参数和期望的输出类型
        String data = FunctionEncoder.encode(function);

        // 创建交易对象
        Transaction transaction = Transaction.createEthCallTransaction(
                null,  // 'from' address is null because it's a read-only call
                this.contractAddressAuction,
                data
        );

        // 发送交易并获取响应
        EthCall response = this.web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

        // 检查是否有错误发生并处理结果
        if (response.hasError()) {
            return "Error: " + response.getError().getMessage();
        }

        // 解码响应数据
        List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        return !result.isEmpty() ? result.get(0).toString() : "No result";
    }


    @Async
    //@PostConstruct
    public void init() {
        log.info("Subscribing to events...");
        subscribeToPetMinted();
        subscribeToLotteryRequestedEvent();
        subscribeToLotteryFulfilledEvent();
        subscribeToPetListedEvent();
        subscribeToPetSoldEvent();
        subscribeToSaleCancelledEvent();
        subscribeToAuctionCreatedEvent();
        subscribeToBidPlacedEvent();
        subscribeToAuctionEndedEvent();
        subscribeToAuctionAutoEndedEvent();
        // subscribeToPEvent();

    }


    public static BigDecimal weiToEther(BigInteger wei) {
        return new BigDecimal(wei).divide(new BigDecimal(WEI_IN_ETHER));
    }


    private EthFilter createFilter(Event event, String contractAddress) {
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
        filter.addSingleTopic(EventEncoder.encode(event));
        return filter;
    }

    // 定义处理错误的方法
    private void handleError(Throwable e) {
        System.err.println("Error occurred: " + e.getMessage());
        e.printStackTrace();
        // 这里可以添加更多的错误处理逻辑，例如重试机制或者报警通知等
    }


    @Async
    public void subscribeToLotteryRequestedEvent() {
        EthFilter filter = createFilter(LOTTERYREQUESTED_EVENT, contractAddressLottery);
        web3j.ethLogFlowable(filter).subscribe(this::handleLotteryRequestedEvent, this::handleError);
    }

    @Async
    public void subscribeToLotteryFulfilledEvent() {
        EthFilter filter = createFilter(LOTTERYFULFILLED_EVENT, contractAddressLottery);
        web3j.ethLogFlowable(filter).subscribe(this::handleLotteryFulfilledEvent, this::handleError);
    }

    @Async
    public void subscribeToPetListedEvent() {
        EthFilter filter = createFilter(PETLISTED_EVENT, contractAddressMarket);
        web3j.ethLogFlowable(filter).subscribe(this::handlePetListedEvent, this::handleError);
    }


    @Async
    public void subscribeToPetSoldEvent() {
        EthFilter filter = createFilter(PETSOLD_EVENT, contractAddressMarket);
        web3j.ethLogFlowable(filter).subscribe(this::handlePetSoldEvent, this::handleError);
    }

    @Async
    public void subscribeToSaleCancelledEvent() {
        EthFilter filter = createFilter(SALECANCELLED_EVENT, contractAddressMarket);
        web3j.ethLogFlowable(filter).subscribe(this::handleSaleCancelledEvent, this::handleError);
    }

    @Async
    public void subscribeToAuctionCreatedEvent() {
        EthFilter filter = createFilter(AUCTIONCREATED_EVENT, contractAddressAuction);
        web3j.ethLogFlowable(filter).subscribe(this::handleAuctionCreatedEvent, this::handleError);
    }

    @Async
    public void subscribeToAuctionEndedEvent() {
        EthFilter filter = createFilter(AUCTIONENDED_EVENT, contractAddressAuction);
        web3j.ethLogFlowable(filter).subscribe(this::handleAuctionEndedEvent, this::handleError);
    }

    @Async
    public void subscribeToAuctionAutoEndedEvent() {
        EthFilter filter = createFilter(AUCTIONAUTOENDED_EVENT, contractAddressAuction);
        web3j.ethLogFlowable(filter).subscribe(this::handleAuctionAutoEnded, this::handleError);
    }

    @Async
    public void subscribeToBidPlacedEvent() {
        EthFilter filter = createFilter(BIDPLACED_EVENT, contractAddressAuction);
        web3j.ethLogFlowable(filter).subscribe(this::handleBidPlacedEvent, this::handleError);
    }

//    @Async
//    public void subscribeToPEvent() {
//        EthFilter filter = createFilter(P_EVENT, contractAddressLottery);
//        web3j.ethLogFlowable(filter).subscribe(this::handlePEvent, this::handleError);
//    }

    @Async
    public void subscribeToPetMinted() {
        EthFilter filter = createFilter(PetNFT_abi.PETMINTED_EVENT, contractAddressNFT);
        web3j.ethLogFlowable(filter).subscribe(this::handlePetMintedEvent, this::handleError);
    }



    private void handleLotteryRequestedEvent(Log log) {
        System.out.println("handling Lottery Requested..........");
        LotteryRequestedEventResponse lotteryRequestedEventResponse = getLotteryRequestedEventFromLog(log);
        MongoCollection<Document> collection = mongoDAO.getCollection("lottery_history");
        Document query = new Document("request_id", String.valueOf(lotteryRequestedEventResponse.requestId));
        long count = collection.countDocuments(query);
        if (count == 0){
            Document document = new Document("requester", lotteryRequestedEventResponse.requester)
                    .append("request_id", String.valueOf(lotteryRequestedEventResponse.requestId))
                    .append("amount", lotteryRequestedEventResponse.amount.intValue());
            collection.insertOne(document);
        }

//        // 构建查询条件
//        Document query = new Document("requester", lotteryRequestedEventResponse.requester)
//                .append("requestId", lotteryRequestedEventResponse.requestId);
//
//        // 检查是否已存在相同的token_id和end_time组合
//        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
//            if (!cursor.hasNext()) {  // 如果没有找到任何匹配的文档，则插入新文档
//                Document document = new Document("requester", lotteryRequestedEventResponse.requester)
//                        .append("request_id", String.valueOf(lotteryRequestedEventResponse.requestId))
//                        .append("amount", lotteryRequestedEventResponse.amount.intValue());
//                collection.insertOne(document);
//            } else {
//                System.out.println("Document with same token_id and end_time already exists.");
//            }
//        }


        System.out.println("requester: "+ lotteryRequestedEventResponse.requester + ", request id: "
                + lotteryRequestedEventResponse.requestId + ", amount: "+ lotteryRequestedEventResponse.amount);

    }


    private void handleLotteryFulfilledEvent(Log log) {
        System.out.println("handling Lottery Fulfilled..........");
        PetLottery_abi.LotteryFulfilledEventResponse lotteryFulfilledEventResponse = getLotteryFulfilledEventFromLog(log);

        List<Integer> tokenIds = new ArrayList<>();

        for (BigInteger tokenId : lotteryFulfilledEventResponse.tokenIds) {
            // 将BigInteger转换为int值并添加到列表中
            tokenIds.add(tokenId.intValue());
        }
        ZonedDateTime datetime = convertToZonedDateTimeUTCPlusOne(lotteryFulfilledEventResponse.timestamp);

        MongoCollection<Document> collection_history = mongoDAO.getCollection("lottery_history");
        MongoCollection<Document> collection_pet = mongoDAO.getCollection("pet");
        collection_history.updateOne(
                Filters.eq("request_id", String.valueOf(lotteryFulfilledEventResponse.requestId)),
                Updates.combine(
                        Updates.set("token_ids", tokenIds),
                        Updates.set("datetime", String.valueOf(datetime))  // 更新状态为可出售
                )
        );

        for (Integer id : tokenIds) {
            collection_pet.updateOne(
                    Filters.eq("id", id),
                    Updates.addToSet("lottery_history", String.valueOf(datetime))
            );
        }





        LotteryHistory lotteryHistory = LotteryHistory.builder()
                .requester(lotteryFulfilledEventResponse.requester)
                .tokenIds(tokenIds)
                .dateTime(datetime)
                .build();
        System.out.println(lotteryHistory);
    }

    private void handlePetListedEvent(Log log) {
        System.out.println("handling Pet listed..........");
        PetListedEventResponse petListedEventResponse = getPetListedEventFromLog(log);
        MongoCollection<Document> collection = mongoDAO.getCollection("pet");
        BigDecimal price = weiToEther(petListedEventResponse.price);
        History history = History.builder()
                .tokenId(petListedEventResponse.tokenId.intValue())
                        .sellerId(petListedEventResponse.seller)
                .price(Double.valueOf(String.valueOf(price)))
                .type("market")
                .build();
        historyMap.put(petListedEventResponse.tokenId.intValue(), history);

        collection.updateOne(
                Filters.eq("id", petListedEventResponse.tokenId.intValue()),
                Updates.combine(
                        Updates.set("market_price", Double.valueOf(String.valueOf(price))),
                        Updates.set("states", "1")  // 更新状态为可出售
                )
        );
        System.out.println("token id: "+ petListedEventResponse.tokenId + ", seller: "+ petListedEventResponse.seller +
                ", price: "+ price);
    }

    // states 0 不能交易
    // states 1 能交易

    private void handlePetSoldEvent(Log log) {
        System.out.println("handling Pet Sold..........");
        PetMarket_abi.PetSoldEventResponse petSoldEventResponse = getPetSoldEventFromLog(log);
        MongoCollection<Document> collection_pet = mongoDAO.getCollection("pet");
        MongoCollection<Document> collection_market = mongoDAO.getCollection("market_history");
        History history = historyMap.get(petSoldEventResponse.tokenId.intValue());
        history.setBuyerId(petSoldEventResponse.buyer);
        history.setDateTime(convertToZonedDateTimeUTCPlusOne(petSoldEventResponse.endTime));

        HistoyDTO histoyDTO = HistoyDTO.builder()
                        .buyerId(history.getBuyerId())
                        .sellerId(history.getSellerId())
                        .dateTime(String.valueOf(history.getDateTime()))
                        .price(history.getPrice())
                        .type(history.getType()).build();

        collection_pet.updateOne(
                Filters.eq("id", petSoldEventResponse.tokenId.intValue()),
                Updates.combine(
                        Updates.set("owner", petSoldEventResponse.buyer),
                        Updates.addToSet("history", histoyDTO),  // 更新价格
                        Updates.set("states", "0")  // 更新状态为不可售
                )
        );

        // 构建查询条件
        Document query = new Document("token_id", petSoldEventResponse.tokenId.intValue())
                .append("end_time", String.valueOf(convertToZonedDateTimeUTCPlusOne(petSoldEventResponse.endTime)));

        // 检查是否已存在相同的token_id和end_time组合
        try (MongoCursor<Document> cursor = collection_market.find(query).iterator()) {
            if (!cursor.hasNext()) {  // 如果没有找到任何匹配的文档，则插入新文档
                System.out.println("------------------------------------");
                Document document = new Document("token_id", history.getTokenId())
                        .append("seller_id", petSoldEventResponse.buyer)
                        .append("buyer_id", petSoldEventResponse.buyer)
                        .append("end_time", String.valueOf(convertToZonedDateTimeUTCPlusOne(petSoldEventResponse.endTime)))
                        .append("price", Double.valueOf(String.valueOf(weiToEther(petSoldEventResponse.price))));
                collection_market.insertOne(document);
            } else {
                System.out.println("Document with same token_id and end_time already exists.");
            }
        }


        System.out.println("token id: "+ petSoldEventResponse.tokenId + ", buyer: "+ petSoldEventResponse.buyer +
                ", market_price: "+ weiToEther(petSoldEventResponse.price) + ", endtime: "+ convertToZonedDateTimeUTCPlusOne(petSoldEventResponse.endTime));

    }


    private void handleSaleCancelledEvent(Log log) {
        System.out.println("handling Sale Cancelled..........");
        SaleCancelledEventResponse saleCancelledEventResponse = getSaleCancelledEventFromLog(log);
        System.out.println("token id:"+ saleCancelledEventResponse.tokenId);
        MongoCollection<Document> collection = mongoDAO.getCollection("pet");
        collection.updateOne(
                Filters.eq("id", saleCancelledEventResponse.tokenId.intValue()),
                Updates.set("states", "0")
        );
    }

    private void handleAuctionCreatedEvent(Log log) {
        System.out.println("handling Auction Created..........");
        AuctionCreatedEventResponse auctionCreatedEventResponse = getAuctionCreatedEventFromLog(log);
        Auction auction = getAuctionById(auctionCreatedEventResponse.tokenId);
        auction.setSeller(auctionCreatedEventResponse.seller);
        auction.setTimestamp(auctionCreatedEventResponse.endTime);

        auction.setStartTime(convertToZonedDateTimeUTCPlusOne(auctionCreatedEventResponse.startTime));
        auction.setEndTime(convertToZonedDateTimeUTCPlusOne(auctionCreatedEventResponse.endTime));
        BigDecimal reservePrice = weiToEther(auctionCreatedEventResponse.reservePrice);
        auction.setReservePrice(Double.valueOf(String.valueOf(reservePrice)));
        auctionList.add(auction);
        System.out.println(auction);

        // 转换为long
        long timestamp = auctionCreatedEventResponse.endTime.longValue();

        // 使用java.util.Date
        Date date = new Date(timestamp * 1000L);
        Date now = new Date();  // 获取当前时间

        // 检查是否应该调度任务
        if (date.after(now)) {  // 如果date是将来的时间
            taskScheduler.schedule(() -> callAuctionEndedFunc(auctionCreatedEventResponse.tokenId), date);
        } else {
            System.out.println("The task is not scheduled because the set time has passed.");
        }
    }

    private void handleBidPlacedEvent(Log log) {
        System.out.println("handling Bid Placed..........");
        BidPlacedEventResponse bidPlacedEventResponse = getBidPlacedEventFromLog(log);

        MongoCollection<Document> collection = mongoDAO.getCollection("pet");
        BigDecimal eth = weiToEther(bidPlacedEventResponse.amount);
        // 向数组中添加一个元素，如果元素已存在，则不会重复添加
        UpdateResult result = collection.updateOne(
                Filters.eq("id", bidPlacedEventResponse.tokenId.intValue()),
                Updates.addToSet("prebid", Double.valueOf(String.valueOf(eth)))
        );
        System.out.println(result);

        System.out.println("token id: "+ bidPlacedEventResponse.tokenId + ", bidder: "+ bidPlacedEventResponse.bidder + ", amount: "+ eth);
    }


    private void handleAuctionEndedEvent(Log log) {
        System.out.println("handling Auction Ended..........");
        PetAuction_abi.AuctionEndedEventResponse auctionEndedEventResponse =  getAuctionEndedEventFromLog(log);
        for (Auction auction : auctionList) {

            if (Objects.equals(auction.getTokenId(), auctionEndedEventResponse.tokenId.intValue()) &&
                    Objects.equals(auction.getTimestamp(), auctionEndedEventResponse.CreatedEndTime)) {
                auction.setHighestBidder(auctionEndedEventResponse.winner);
                BigDecimal highestBid = weiToEther(auctionEndedEventResponse.amount);
                auction.setHighestBid(Double.valueOf(String.valueOf(highestBid)));

                MongoCollection<Document> collection = mongoDAO.getCollection("auction_history");


                // 构建查询条件
                Document query = new Document("token_id", auction.getTokenId())
                        .append("end_time", String.valueOf(auction.getEndTime()));

                // 检查是否已存在相同的token_id和end_time组合
                try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
                    if (!cursor.hasNext()) {  // 如果没有找到任何匹配的文档，则插入新文档
                        Document document = new Document("token_id", auction.getTokenId())
                                .append("seller", auction.getSeller())
                                .append("start_time", String.valueOf(auction.getStartTime()))
                                .append("end_time", String.valueOf(auction.getEndTime()))
                                .append("timestamp", String.valueOf(auction.getTimestamp()))
                                .append("highest_bid", Double.valueOf(String.valueOf(auction.getHighestBid())))
                                .append("highest_bidder", auction.getHighestBidder())
                                .append("reserve_price", auction.getReservePrice());
                        collection.insertOne(document);
                        System.out.println(auction);
                    } else {
                        System.out.println("Document with same token_id and end_time already exists.");
                    }
                }
//                // 创建一个复合唯一索引
//                IndexOptions indexOptions = new IndexOptions().unique(true);
//                collection.createIndex(Indexes.ascending("token_id", "end_time"), indexOptions);
//                Document document = new Document("token_id", auction.getTokenId())
//                        .append("seller", auction.getSeller())
//                        .append("start_time", String.valueOf(auction.getStartTime()))
//                        .append("end_time", String.valueOf(auction.getEndTime()))
//                        .append("timestamp", String.valueOf(auction.getTimestamp()))
//                        .append("highest_bid", Double.valueOf(String.valueOf(auction.getHighestBid())))
//                        .append("highest_bidder", auction.getHighestBidder())
//                        .append("reserve_price", auction.getReservePrice());
//                // 插入文档到集合
//                collection.insertOne(document);

            }
        }




        if (!Objects.equals(auctionEndedEventResponse.winner, "0x0000000000000000000000000000000000000000")){

            MongoCollection<Document> collection = mongoDAO.getCollection("pet");
            System.out.println("***********************"+auctionEndedEventResponse.tokenId.intValue());
            BigDecimal eth = weiToEther(auctionEndedEventResponse.amount);
            // 向数组中添加一个元素，如果元素已存在，则不会重复添加
            collection.updateOne(
                    Filters.eq("id", auctionEndedEventResponse.tokenId.intValue()),
                    Updates.combine(
                            Updates.set("owner", auctionEndedEventResponse.winner),
                            Updates.set("auction_price", Double.valueOf(String.valueOf(eth)))
                    )
            );

        }

    }

    private void handleAuctionAutoEnded(Log log) {
        System.out.println("handling Auction Auto Ended..........");
        PetAuction_abi.AuctionAutoEndedEventResponse auctionAutoEndedEventResponse =  getAuctionAutoEndedEventFromLog(log);
        System.out.println("token id: "+auctionAutoEndedEventResponse.tokenId + ", winner: "
                +auctionAutoEndedEventResponse.winner + ", amoumt: "+auctionAutoEndedEventResponse.amount);
    }


    private void handlePetMintedEvent(Log log) {
        System.out.println("handling Pet Minted..........");
        PetNFT_abi.PetMintedEventResponse petMintedEventResponse = PetNFT_abi.getPetMintedEventFromLog(log);
        List<String> attributes = new ArrayList<>();
        attributes.add(petMintedEventResponse.appearance);
        attributes.add(petMintedEventResponse.character);
        Pet pet = Pet.builder()
                .id(petMintedEventResponse.tokenId.intValue())
                .owner(petMintedEventResponse.to)
                .petclass(String.valueOf(petMintedEventResponse.level))
                .title(petMintedEventResponse.name)
                .attributes(attributes)
                .description(petMintedEventResponse.description)
                .imageUrl(petMintedEventResponse.url)
                .jsonUri(petMintedEventResponse.uri)
                .build();
        System.out.println(pet);



//        Double [] notBidded = new Double[1];
//        notBidded[0] = (double) -1;
        MongoCollection<Document> collection = mongoDAO.getCollection("pet");
        Document query = new Document("id", pet.getId());
        long count = collection.countDocuments(query);
        if (count == 0){
            List<Double> prebid = new ArrayList<>();
            Document doc = new Document("id", pet.getId())
                    .append("attributes", pet.getAttributes())
                    .append("image", pet.getImageUrl())
                    .append("json_uri", pet.getJsonUri())
                    .append("petclass", pet.getPetclass())
                    .append("title", pet.getTitle())
                    .append("description", pet.getDescription())
                    .append("prebid", prebid)
                    .append("auction_price", pet.getAuctionPrice())
                    .append("market_price", pet.getMarketPrice())
                    .append("owner", pet.getOwner())
                    .append("states", "0");
            collection.insertOne(doc);
        }

    }

    private Auction getAuctionById(BigInteger tokenId) {
        return Auction.builder().tokenId(tokenId.intValue()).build();
    }

    private Pet getPetById(Integer tokenId) {
        return Pet.builder().id(tokenId).build();
    }

}

