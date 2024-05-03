package com.uobfintech.nftpictures.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.uobfintech.nftpictures.service.ContractService;

import static org.web3j.tx.Contract.staticExtractEventParameters;

@Slf4j
@Service
public class ContractServiceImpl implements ContractService {
    private final Web3j web3j;
    private final String contractAddress;

    // Assume a very low gas price since it's a read operation.


    public ContractServiceImpl(Web3j web3j) {
        this.web3j = web3j;
        this.contractAddress = "0x025Cce1e9cC395E8e82B87476ed61c0b4566e77b"; // 从配置中读取或者作为参数传递
    }

    @Scheduled(fixedRate = 600000) // 每600000毫秒（即十分钟）执行一次
    public void callContractFunctionPeriodically() {
        try {
            String functionName = "yourFunctionName";
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
                Arrays.asList(new Uint256(BigInteger.valueOf(123))),  // Your input parameters
                Collections.singletonList(new org.web3j.abi.TypeReference<Uint256>() {}));

        String encodedFunction = FunctionEncoder.encode(function);

        // Default gas price and limit, adjust according to your contract's needs
        BigInteger gasPrice = BigInteger.valueOf(0); // Usually you would use a non-zero gas price
        BigInteger gasLimit = BigInteger.valueOf(4_700_000); // Default gas limit for Ethereum transactions

        Transaction transaction = Transaction.createEthCallTransaction(null, contractAddress, encodedFunction);
        EthCall response = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

        if (response.hasError()) {
            return "Error: " + response.getError().getMessage();
        }

        List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        return !result.isEmpty() ? result.get(0).getValue().toString() : "No result";
    }




    @Async
    @PostConstruct
    public void listenToMyEvent() {

        // 定义事件
        Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {}, // "indexed from"
                        new TypeReference<Address>(true) {}, // "indexed to"
                        new TypeReference<Uint256>(false) {} // "value"
                )
        );
        String eventSignature = EventEncoder.encode(event);
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST, contractAddress).addSingleTopic(eventSignature);




        web3j.ethLogFlowable(filter).subscribe(
                log -> {
                    EventValues eventValues = staticExtractEventParameters(event, log);
                    System.out.println("Event data: " + eventValues.getIndexedValues());


                    List<String> topics = log.getTopics();
                    String eventData = log.getData();

                    // 解码事件的非索引参数
                    List<Type> nonIndexedValues = FunctionReturnDecoder.decode(
                            eventData, event.getNonIndexedParameters());

                    // 从日志中提取具体的值
                    Address fromAddress = (Address) nonIndexedValues.get(0);
                    Uint256 value = (Uint256) nonIndexedValues.get(1);

                    // 输出或处理事件的值
                    System.out.println("From: " + fromAddress.getValue());
                    System.out.println("Value: " + value.getValue());
                }, error -> log.error("11111111111 "+error));
    }
}
