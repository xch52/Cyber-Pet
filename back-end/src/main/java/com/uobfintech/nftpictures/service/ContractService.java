package com.uobfintech.nftpictures.service;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;

public interface ContractService {

    void init();
    void callContractFunctionPeriodically();
    String callContractFunction(@RequestParam String functionName) throws Exception;

    String callContractFunction(String functionName, List<Type> inputParameters, List<TypeReference<?>> outputParameters) throws Exception;

    ZonedDateTime convertToZonedDateTimeUTCPlusOne(BigInteger timestamp);
}
