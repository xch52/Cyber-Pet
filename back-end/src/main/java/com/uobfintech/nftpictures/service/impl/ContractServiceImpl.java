package com.uobfintech.nftpictures.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;

import com.uobfintech.nftpictures.service.ContractService;

import static org.web3j.tx.Contract.staticExtractEventParameters;

@Slf4j
@Service
public class ContractServiceImpl implements ContractService {
    private final Web3j web3j;
    private final String contractAddress;

    public ContractServiceImpl(Web3j web3j) {
        this.web3j = web3j;
        this.contractAddress = "0xf0Cf680CC6ed287457a61EB4d25d6C8F1fCdFFe4"; // 从配置中读取或者作为参数传递
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
