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
    private Map<Integer, History> historyMap = new HashMap<>();
    // @Autowired
    private MongoDAO mongoDAO;
    @Autowired
    private TaskScheduler taskScheduler;
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


    // Converts a blockchain timestamp to a time with time zone information
    public ZonedDateTime convertToZonedDateTimeUTCPlusOne(BigInteger timestamp) {
        // Convert seconds to milliseconds
        long milliseconds = timestamp.multiply(BigInteger.valueOf(1000)).longValue();
        Instant instant = Instant.ofEpochMilli(milliseconds);

        // Convert to ZonedDateTime in UTC+1 / London time zone
        return instant.atZone(ZoneId.of("Europe/London"));
    }


    @Async
    public void callAuctionEndedFunc(BigInteger tokenId) {
        try {
            Credentials credentials = Credentials.create("b67d61efaab1a9d65a53221c8cfbd89d38c72d6ff0ed40b8e4e4a56af607cb0b");
            PetAuction_abi petAuctionAbi = new PetAuction_abi(contractAddressAuction, web3j, credentials, new DefaultGasProvider());
            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = petAuctionAbi.endAuction(tokenId);

            try {
                TransactionReceipt result = remoteFunctionCall.send();
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


    public String callContractFunction(String functionName) throws Exception {
        Function function = new Function(
                functionName,
                java.util.Collections.emptyList(),
                // Arrays.asList(new Uint256(BigInteger.valueOf(123))),  // Your input parameters
                Collections.singletonList(new org.web3j.abi.TypeReference<Uint256>() {
                }));

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

        // Encodes the function call, containing the input parameters and the expected output type
        String data = FunctionEncoder.encode(function);

        Transaction transaction = Transaction.createEthCallTransaction(
                null,  // 'from' address is null because it's a read-only call
                this.contractAddressAuction,
                data
        );
        EthCall response = this.web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

        if (response.hasError()) {
            return "Error: " + response.getError().getMessage();
        }

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
    }


    public static BigDecimal weiToEther(BigInteger wei) {
        return new BigDecimal(wei).divide(new BigDecimal(WEI_IN_ETHER));
    }


    private EthFilter createFilter(Event event, String contractAddress) {
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
        filter.addSingleTopic(EventEncoder.encode(event));
        return filter;
    }

    // Define ways to handle errors
    private void handleError(Throwable e) {
        System.err.println("Error occurred: " + e.getMessage());
        e.printStackTrace();
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
    public void subscribeToBidPlacedEvent() {
        EthFilter filter = createFilter(BIDPLACED_EVENT, contractAddressAuction);
        web3j.ethLogFlowable(filter).subscribe(this::handleBidPlacedEvent, this::handleError);
    }


    @Async
    public void subscribeToPetMinted() {
        EthFilter filter = createFilter(PetNFT_abi.PETMINTED_EVENT, contractAddressNFT);
        web3j.ethLogFlowable(filter).subscribe(this::handlePetMintedEvent, this::handleError);
    }


    private void handleLotteryRequestedEvent(Log log) {
        LotteryRequestedEventResponse lotteryRequestedEventResponse = getLotteryRequestedEventFromLog(log);
        MongoCollection<Document> collection = mongoDAO.getCollection("lottery_history");
        Document query = new Document("request_id", String.valueOf(lotteryRequestedEventResponse.requestId));
        long count = collection.countDocuments(query);
        if (count == 0) {
            Document document = new Document("requester", lotteryRequestedEventResponse.requester)
                    .append("request_id", String.valueOf(lotteryRequestedEventResponse.requestId))
                    .append("amount", lotteryRequestedEventResponse.amount.intValue());
            collection.insertOne(document);
        }

//        System.out.println("requester: "+ lotteryRequestedEventResponse.requester + ", request id: "
//                + lotteryRequestedEventResponse.requestId + ", amount: "+ lotteryRequestedEventResponse.amount);

    }


    private void handleLotteryFulfilledEvent(Log log) {
        PetLottery_abi.LotteryFulfilledEventResponse lotteryFulfilledEventResponse = getLotteryFulfilledEventFromLog(log);

        List<Integer> tokenIds = new ArrayList<>();

        for (BigInteger tokenId : lotteryFulfilledEventResponse.tokenIds) {
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

//        LotteryHistory lotteryHistory = LotteryHistory.builder()
//                .requester(lotteryFulfilledEventResponse.requester)
//                .requestId(lotteryFulfilledEventResponse.requestId.toString())
//                .tokenIds(tokenIds)
//                .dateTime(datetime)
//                .build();
//        System.out.println(lotteryHistory);
    }

    private void handlePetListedEvent(Log log) {
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
    }

    // states 0 不能交易
    // states 1 能交易

    private void handlePetSoldEvent(Log log) {
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
                        Updates.addToSet("history", histoyDTO),  // Add history
                        Updates.set("states", "0")  // Update its status to unsellable (states = 0)
                )
        );

        // Construct query criteria
        Document query = new Document("token_id", petSoldEventResponse.tokenId.intValue())
                .append("end_time", String.valueOf(convertToZonedDateTimeUTCPlusOne(petSoldEventResponse.endTime)));

        // Check whether the same combination of token_id and end_time already exists
        try (MongoCursor<Document> cursor = collection_market.find(query).iterator()) {
            if (!cursor.hasNext()) {  // If no matching documents are found, a new document is inserted
                System.out.println("------------------------------------");
                Document document = new Document("token_id", history.getTokenId())
                        .append("seller_id", petSoldEventResponse.buyer)
                        .append("buyer_id", petSoldEventResponse.buyer)
                        .append("end_time", String.valueOf(convertToZonedDateTimeUTCPlusOne(petSoldEventResponse.endTime)))
                        .append("price", Double.valueOf(String.valueOf(weiToEther(petSoldEventResponse.price))));
                collection_market.insertOne(document);
            }
        }
    }


    private void handleSaleCancelledEvent(Log log) {
        System.out.println("handling Sale Cancelled..........");
        SaleCancelledEventResponse saleCancelledEventResponse = getSaleCancelledEventFromLog(log);
        System.out.println("token id:" + saleCancelledEventResponse.tokenId);
        MongoCollection<Document> collection = mongoDAO.getCollection("pet");
        collection.updateOne(
                Filters.eq("id", saleCancelledEventResponse.tokenId.intValue()),
                Updates.set("states", "0")
        );
    }

    private void handleAuctionCreatedEvent(Log log) {
        AuctionCreatedEventResponse auctionCreatedEventResponse = getAuctionCreatedEventFromLog(log);
        MongoCollection<Document> collection = mongoDAO.getCollection("auction_history");
        // Construct query criteria
        Document query = new Document("token_id", auctionCreatedEventResponse.tokenId.intValue())
                .append("end_time", String.valueOf(convertToZonedDateTimeUTCPlusOne(auctionCreatedEventResponse.endTime)));

        // Check whether the same combination of token_id and end_time already exists
        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            if (cursor.hasNext()) {  // If matching documents are found, then return
                return;
            }
        }


        for (Auction auction : auctionList){
            if (auction.getTokenId() == auctionCreatedEventResponse.tokenId.intValue()
                    && auction.getEndTime() == convertToZonedDateTimeUTCPlusOne(auctionCreatedEventResponse.endTime)){
                return;
            }
        }
        Auction auction = getAuctionById(auctionCreatedEventResponse.tokenId);
        auction.setSeller(auctionCreatedEventResponse.seller);
        auction.setTimestamp(auctionCreatedEventResponse.endTime);

        auction.setStartTime(convertToZonedDateTimeUTCPlusOne(auctionCreatedEventResponse.startTime));
        auction.setEndTime(convertToZonedDateTimeUTCPlusOne(auctionCreatedEventResponse.endTime));
        BigDecimal reservePrice = weiToEther(auctionCreatedEventResponse.reservePrice);
        auction.setReservePrice(Double.valueOf(String.valueOf(reservePrice)));
        auctionList.add(auction);


        // Convert to long
        long timestamp = auctionCreatedEventResponse.endTime.longValue();

        Date date = new Date(timestamp * 1000L);
        Date now = new Date();  // Get current time

        // Check whether tasks should be scheduled
        if (now.after(date)) {
            taskScheduler.schedule(() -> callAuctionEndedFunc(auctionCreatedEventResponse.tokenId), date);
        } else {
            // System.out.println("The task is not scheduled because the set time has passed.");
        }
    }

    private void handleBidPlacedEvent(Log log) {
        BidPlacedEventResponse bidPlacedEventResponse = getBidPlacedEventFromLog(log);

        MongoCollection<Document> collection = mongoDAO.getCollection("pet");
        BigDecimal eth = weiToEther(bidPlacedEventResponse.amount);
        // Adds an element to the array and does not repeat the addition if the element already exists
        collection.updateOne(
                Filters.eq("id", bidPlacedEventResponse.tokenId.intValue()),
                Updates.addToSet("prebid", Double.valueOf(String.valueOf(eth)))
        );

    }


    private void handleAuctionEndedEvent(Log log) {
        PetAuction_abi.AuctionEndedEventResponse auctionEndedEventResponse = getAuctionEndedEventFromLog(log);
        for (Auction auction : auctionList) {

            if (Objects.equals(auction.getTokenId(), auctionEndedEventResponse.tokenId.intValue()) &&
                    Objects.equals(auction.getTimestamp(), auctionEndedEventResponse.CreatedEndTime)) {
                auction.setHighestBidder(auctionEndedEventResponse.winner);
                BigDecimal highestBid = weiToEther(auctionEndedEventResponse.amount);
                auction.setHighestBid(Double.valueOf(String.valueOf(highestBid)));

                MongoCollection<Document> collection = mongoDAO.getCollection("auction_history");

                // Construct query criteria
                Document query = new Document("token_id", auction.getTokenId())
                        .append("end_time", String.valueOf(auction.getEndTime()));

                // Check whether the same combination of token_id and end_time already exists
                try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
                    if (!cursor.hasNext()) {  // If no matching documents are found, a new document is inserted
                        Document document = new Document("token_id", auction.getTokenId())
                                .append("seller", auction.getSeller())
                                .append("start_time", String.valueOf(auction.getStartTime()))
                                .append("end_time", String.valueOf(auction.getEndTime()))
                                .append("timestamp", String.valueOf(auction.getTimestamp()))
                                .append("highest_bid", Double.valueOf(String.valueOf(auction.getHighestBid())))
                                .append("highest_bidder", auction.getHighestBidder())
                                .append("reserve_price", auction.getReservePrice());
                        collection.insertOne(document);

                        System.out.println("Ended: "+auction);
                    } else {
                        // System.out.println("Document with same token_id and end_time already exists.");
                    }
                }
            }
        }


        if (!Objects.equals(auctionEndedEventResponse.winner, "0x0000000000000000000000000000000000000000")) {

            MongoCollection<Document> collection = mongoDAO.getCollection("pet");
            BigDecimal eth = weiToEther(auctionEndedEventResponse.amount);

            collection.updateOne(
                    Filters.eq("id", auctionEndedEventResponse.tokenId.intValue()),
                    Updates.combine(
                            Updates.set("owner", auctionEndedEventResponse.winner),
                            Updates.set("auction_price", Double.valueOf(String.valueOf(eth)))
                    )
            );
        }
    }


    private void handlePetMintedEvent(Log log) {
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

        MongoCollection<Document> collection = mongoDAO.getCollection("pet");
        Document query = new Document("id", pet.getId());
        long count = collection.countDocuments(query);
        if (count == 0) {
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

}

