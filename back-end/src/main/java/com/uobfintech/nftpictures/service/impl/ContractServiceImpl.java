package com.uobfintech.nftpictures.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.Log;
import java.time.ZoneId;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.uobfintech.nftpictures.abi.PetAuction_abi;
import com.uobfintech.nftpictures.abi.PetLottery_abi;
import com.uobfintech.nftpictures.abi.PetMarket_abi;
import com.uobfintech.nftpictures.entity.Auction;
import com.uobfintech.nftpictures.entity.LotteryHistory;
import com.uobfintech.nftpictures.service.ContractService;

import static com.uobfintech.nftpictures.abi.PetAuction_abi.*;
import static com.uobfintech.nftpictures.abi.PetLottery_abi.*;
import static com.uobfintech.nftpictures.abi.PetMarket_abi.*;

@Slf4j
@Service
@EnableAsync
public class ContractServiceImpl implements ContractService {
    private final Web3j web3j;
    private final String contractAddressLottery;
    private final String contractAddressAuction;
    private final String contractAddressMarket;

    private List<Auction> auctionList;

    // Assume a very low gas price since it's a read operation.


    public ContractServiceImpl(Web3j web3j, String contractAddressLottery, String contractAddressAuction, String contractAddressMarket) {
        this.web3j = web3j;
        this.contractAddressLottery = contractAddressLottery;
        this.contractAddressAuction = contractAddressAuction;
        this.contractAddressMarket = contractAddressMarket;
        this.auctionList = new ArrayList<>();
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

        return instant.atZone(ZoneId.of("UTC+1"));
    }

    @Async
    @Scheduled(fixedRate = 600000) // 每600000毫秒（即十分钟）执行一次
    public void callContractFunctionPeriodically() {
        try {
//            RemoteFunctionCall<TransactionReceipt> remoteFunctionCall
//                    = petAuctionAbi.checkAndEndAuctions();
//
//            String encodedFunction = remoteFunctionCall.encodeFunctionCall();
//            Transaction transaction = Transaction.createEthCallTransaction(null, contractAddressAuction, encodedFunction);
//            EthCall response = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
//
//            if (response.hasError()) {
//                System.out.println("Error: " + response.getError().getMessage());
//            }
//            List<Type> result = remoteFunctionCall.decodeFunctionResponse(response.getValue());
//
//            if (!result.isEmpty()){
//                System.out.println(result.get(0).getValue().toString());
//            }else {
//                System.out.println("No result");
//            }
            String functionName = "checkAndEndAuctions";
            // 调用具体的合约函数
            String result = callContractFunction(functionName);
            System.out.println("Contract function result: " + result);

        } catch (Exception e) {
            System.err.println("Error during contract function call: " + e.getMessage());
        }
    }


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
        subscribeToLotteryRequestedEvent();
        subscribeToLotteryFulfilledEvent();
        subscribeToPetListedEvent();
        subscribeToPetSoldEvent();
        subscribeToSaleCancelledEvent();
        subscribeToAuctionCreatedEvent();
        subscribeToBidPlacedEvent();
        subscribeToAuctionEndedEvent();
        subscribeToPEvent();

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
    public void subscribeToBidPlacedEvent() {
        EthFilter filter = createFilter(BIDPLACED_EVENT, contractAddressAuction);
        web3j.ethLogFlowable(filter).subscribe(this::handleBidPlacedEvent, this::handleError);
    }

    @Async
    public void subscribeToPEvent() {
        EthFilter filter = createFilter(P_EVENT, contractAddressLottery);
        web3j.ethLogFlowable(filter).subscribe(this::handlePEvent, this::handleError);
    }



    private void handleLotteryRequestedEvent(Log log) {
        System.out.println("handling Lottery Requested..........");
        LotteryRequestedEventResponse lotteryRequestedEventResponse = getLotteryRequestedEventFromLog(log);
        System.out.println("requester:"+ lotteryRequestedEventResponse.requester);
        System.out.println("request id:"+ Arrays.toString(lotteryRequestedEventResponse.requestId));
        System.out.println("amount:"+ lotteryRequestedEventResponse.amount);
    }


    private void handleLotteryFulfilledEvent(Log log) {
        System.out.println("handling Lottery Fulfilled..........");
        PetLottery_abi.LotteryFulfilledEventResponse lotteryFulfilledEventResponse = getLotteryFulfilledEventFromLog(log);
        LotteryHistory lotteryHistory = LotteryHistory.builder()
                .requester(lotteryFulfilledEventResponse.requester)
                .tokenIds(lotteryFulfilledEventResponse.tokenIds)
                .build();

        System.out.println(lotteryHistory);
        //System.out.println("requester:"+ lotteryFulfilledEventResponse.requester);
        //System.out.println("token id:"+ lotteryFulfilledEventResponse.tokenIds);
    }

    private void handlePetListedEvent(Log log) {
        System.out.println("handling Pet listed..........");
        PetListedEventResponse petListedEventResponse = getPetListedEventFromLog(log);
        System.out.println("token id:"+ petListedEventResponse.tokenId);
        System.out.println("seller"+ petListedEventResponse.seller);
        System.out.println("price:"+ petListedEventResponse.price);
    }



    private void handlePetSoldEvent(Log log) {
        System.out.println("handling Pet Sold..........");
        PetMarket_abi.PetSoldEventResponse petSoldEventResponse = getPetSoldEventFromLog(log);
        System.out.println("token id:"+ petSoldEventResponse.tokenId);
        System.out.println("buyer:"+ petSoldEventResponse.buyer);
        System.out.println("price:"+ petSoldEventResponse.price);
        if (petSoldEventResponse.endTime != null)
            System.out.println("price:"+ petSoldEventResponse.endTime);
    }


    private void handleSaleCancelledEvent(Log log) {
        System.out.println("handling Sale Cancelled..........");
        SaleCancelledEventResponse saleCancelledEventResponse = getSaleCancelledEventFromLog(log);
        System.out.println("token id:"+ saleCancelledEventResponse.tokenId);
    }

    private void handleAuctionCreatedEvent(Log log) {
        System.out.println("handling Auction Created..........");
        AuctionCreatedEventResponse auctionCreatedEventResponse = getAuctionCreatedEventFromLog(log);
        Auction auction = getAuctionById(auctionCreatedEventResponse.tokenId);
        auction.setSeller(auctionCreatedEventResponse.seller);

        auction.setStartTime(convertToZonedDateTimeUTCPlusOne(auctionCreatedEventResponse.startTime));
        auction.setEndTime(convertToZonedDateTimeUTCPlusOne(auctionCreatedEventResponse.endTime));
        auctionList.add(auction);
        System.out.println(auction);
//        System.out.println("token id:"+ auctionCreatedEventResponse.tokenId);
//        System.out.println("seller:"+ auctionCreatedEventResponse.seller);
//        System.out.println("reserve Price:"+ auctionCreatedEventResponse.reservePrice);
//        System.out.println("start Time:"+ auctionCreatedEventResponse.startTime);
//        System.out.println("end Time:"+ auctionCreatedEventResponse.endTime);
    }

    private void handleBidPlacedEvent(Log log) {
        System.out.println("handling Bid Placed..........");
        BidPlacedEventResponse bidPlacedEventResponse = getBidPlacedEventFromLog(log);
        System.out.println("token id:"+ bidPlacedEventResponse.tokenId);
        System.out.println("bidder:"+ bidPlacedEventResponse.bidder);
        System.out.println("amount:"+ bidPlacedEventResponse.amount);
    }


    private void handleAuctionEndedEvent(Log log) {
        System.out.println("handling Auction Ended..........");
        PetAuction_abi.AuctionEndedEventResponse auctionEndedEventResponse =  getAuctionEndedEventFromLog(log);
        for (Auction auction : auctionList) {
            if (Objects.equals(auction.getTokenId(), auctionEndedEventResponse.tokenId)) {
                auction.setHighestBidder(auctionEndedEventResponse.winner);
                auction.setHighestBid(auctionEndedEventResponse.amount);
                System.out.println(auction);
            }
        }

//        System.out.println("token id: " + auctionEndedEventResponse.tokenId);
//        System.out.println("winner: "+auctionEndedEventResponse.winner);
//        System.out.println("amount: "+auctionEndedEventResponse.amount);
    }

    private void handlePEvent(Log log) {
        System.out.println("handling PPPP....");
        PEventResponse pEventResponse = getPEventFromLog(log);
        System.out.println("t: "+pEventResponse.t);

    }

    private Auction getAuctionById(BigInteger tokenId) {
        return Auction.builder().tokenId(tokenId).build();
    }

}
