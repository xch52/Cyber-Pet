package com.uobfintech.nftpictures.abi;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.3.
 */
@SuppressWarnings("rawtypes")
public class PetMarket_abi extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b50604051610f94380380610f94833981810160405281019061003191906100dc565b60015f819055508060015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050610107565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6100ab82610082565b9050919050565b6100bb816100a1565b81146100c5575f80fd5b50565b5f815190506100d6816100b2565b92915050565b5f602082840312156100f1576100f061007e565b5b5f6100fe848285016100c8565b91505092915050565b610e80806101145f395ff3fe608060405260043610610049575f3560e01c806368a7ac5b1461004d5780636dd98867146100755780639edded111461009f578063bd94b005146100bb578063ea58f56c146100e3575b5f80fd5b348015610058575f80fd5b50610073600480360381019061006e91906108d3565b610121565b005b348015610080575f80fd5b50610089610435565b604051610096919061098b565b60405180910390f35b6100b960048036038101906100b491906109a4565b61045a565b005b3480156100c6575f80fd5b506100e160048036038101906100dc91906109a4565b61069a565b005b3480156100ee575f80fd5b50610109600480360381019061010491906109a4565b6107f5565b60405161011893929190610a18565b60405180910390f35b3373ffffffffffffffffffffffffffffffffffffffff1660015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16636352211e846040518263ffffffff1660e01b81526004016101929190610a4d565b602060405180830381865afa1580156101ad573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906101d19190610a90565b73ffffffffffffffffffffffffffffffffffffffff1614610227576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161021e90610b3b565b60405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff1660015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663081812fc846040518263ffffffff1660e01b81526004016102989190610a4d565b602060405180830381865afa1580156102b3573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102d79190610a90565b73ffffffffffffffffffffffffffffffffffffffff161461032d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161032490610bc9565b60405180910390fd5b60405180606001604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018281526020016001151581525060025f8481526020019081526020015f205f820151815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101556040820151816002015f6101000a81548160ff0219169083151502179055509050503373ffffffffffffffffffffffffffffffffffffffff16827f187f616f90eaf716f9196a8f2eaead21fec5a107062159e6cc7e92b70ba9bca9836040516104299190610a4d565b60405180910390a35050565b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b610462610846565b5f60025f8381526020019081526020015f209050806002015f9054906101000a900460ff166104c6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104bd90610c31565b60405180910390fd5b806001015434101561050d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161050490610c99565b60405180910390fd5b5f816002015f6101000a81548160ff02191690831515021790555060015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166342842e0e825f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1633856040518463ffffffff1660e01b81526004016105a893929190610cb7565b5f604051808303815f87803b1580156105bf575f80fd5b505af11580156105d1573d5f803e3d5ffd5b50505050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc3490811502906040515f60405180830381858888f1935050505015801561063a573d5f803e3d5ffd5b503373ffffffffffffffffffffffffffffffffffffffff16827fea90122f42bcf76a252d2eb59092578d1f9c468eda2783848df33dce5bca8b5583600101546040516106869190610a4d565b60405180910390a350610697610893565b50565b3373ffffffffffffffffffffffffffffffffffffffff1660025f8381526020019081526020015f205f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161461073a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161073190610d5c565b60405180910390fd5b60025f8281526020019081526020015f206002015f9054906101000a900460ff1661079a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161079190610dc4565b60405180910390fd5b5f60025f8381526020019081526020015f206002015f6101000a81548160ff021916908315150217905550807f2c56893f6f6026d19bd17b7d05c9f15c522de1ae2b1c3a825f91a73c799321f260405160405180910390a250565b6002602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015490806002015f9054906101000a900460ff16905083565b60025f540361088a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161088190610e2c565b60405180910390fd5b60025f81905550565b60015f81905550565b5f80fd5b5f819050919050565b6108b2816108a0565b81146108bc575f80fd5b50565b5f813590506108cd816108a9565b92915050565b5f80604083850312156108e9576108e861089c565b5b5f6108f6858286016108bf565b9250506020610907858286016108bf565b9150509250929050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f819050919050565b5f61095361094e61094984610911565b610930565b610911565b9050919050565b5f61096482610939565b9050919050565b5f6109758261095a565b9050919050565b6109858161096b565b82525050565b5f60208201905061099e5f83018461097c565b92915050565b5f602082840312156109b9576109b861089c565b5b5f6109c6848285016108bf565b91505092915050565b5f6109d982610911565b9050919050565b6109e9816109cf565b82525050565b6109f8816108a0565b82525050565b5f8115159050919050565b610a12816109fe565b82525050565b5f606082019050610a2b5f8301866109e0565b610a3860208301856109ef565b610a456040830184610a09565b949350505050565b5f602082019050610a605f8301846109ef565b92915050565b610a6f816109cf565b8114610a79575f80fd5b50565b5f81519050610a8a81610a66565b92915050565b5f60208284031215610aa557610aa461089c565b5b5f610ab284828501610a7c565b91505092915050565b5f82825260208201905092915050565b7f596f75206d757374206f776e207468652070657420746f206c697374206974205f8201527f666f722073616c65000000000000000000000000000000000000000000000000602082015250565b5f610b25602883610abb565b9150610b3082610acb565b604082019050919050565b5f6020820190508181035f830152610b5281610b19565b9050919050565b7f4d61726b6574206d75737420626520617070726f76656420746f207472616e735f8201527f6665722070657400000000000000000000000000000000000000000000000000602082015250565b5f610bb3602783610abb565b9150610bbe82610b59565b604082019050919050565b5f6020820190508181035f830152610be081610ba7565b9050919050565b7f5468697320706574206973206e6f7420666f722073616c6500000000000000005f82015250565b5f610c1b601883610abb565b9150610c2682610be7565b602082019050919050565b5f6020820190508181035f830152610c4881610c0f565b9050919050565b7f496e73756666696369656e742066756e64732073656e740000000000000000005f82015250565b5f610c83601783610abb565b9150610c8e82610c4f565b602082019050919050565b5f6020820190508181035f830152610cb081610c77565b9050919050565b5f606082019050610cca5f8301866109e0565b610cd760208301856109e0565b610ce460408301846109ef565b949350505050565b7f4f6e6c79207468652073656c6c65722063616e2063616e63656c2074686520735f8201527f616c650000000000000000000000000000000000000000000000000000000000602082015250565b5f610d46602383610abb565b9150610d5182610cec565b604082019050919050565b5f6020820190508181035f830152610d7381610d3a565b9050919050565b7f53616c65206973206e6f742061637469766500000000000000000000000000005f82015250565b5f610dae601283610abb565b9150610db982610d7a565b602082019050919050565b5f6020820190508181035f830152610ddb81610da2565b9050919050565b7f5265656e7472616e637947756172643a207265656e7472616e742063616c6c005f82015250565b5f610e16601f83610abb565b9150610e2182610de2565b602082019050919050565b5f6020820190508181035f830152610e4381610e0a565b905091905056fea2646970667358221220a5267e9b303efe8f41b93ac295844641ed222ace39547d4fa87973c5725eb53264736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_BUYPET = "buyPet";

    public static final String FUNC_CANCELSALE = "cancelSale";

    public static final String FUNC_LISTPETFORSALE = "listPetForSale";

    public static final String FUNC_PETNFT = "petNFT";

    public static final String FUNC_PETSALES = "petSales";

    public static final Event PETLISTED_EVENT = new Event("PetListed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PETSOLD_EVENT = new Event("PetSold", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SALECANCELLED_EVENT = new Event("SaleCancelled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected PetMarket_abi(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PetMarket_abi(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PetMarket_abi(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PetMarket_abi(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<PetListedEventResponse> getPetListedEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PETLISTED_EVENT, transactionReceipt);
//        ArrayList<PetListedEventResponse> responses = new ArrayList<PetListedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            PetListedEventResponse typedResponse = new PetListedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.seller = (String) eventValues.getIndexedValues().get(1).getValue();
//            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static PetListedEventResponse getPetListedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PETLISTED_EVENT, log);
        PetListedEventResponse typedResponse = new PetListedEventResponse();
        typedResponse.log = log;
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.seller = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<PetListedEventResponse> petListedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPetListedEventFromLog(log));
    }

    public Flowable<PetListedEventResponse> petListedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PETLISTED_EVENT));
        return petListedEventFlowable(filter);
    }

//    public static List<PetSoldEventResponse> getPetSoldEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PETSOLD_EVENT, transactionReceipt);
//        ArrayList<PetSoldEventResponse> responses = new ArrayList<PetSoldEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            PetSoldEventResponse typedResponse = new PetSoldEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
//            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static PetSoldEventResponse getPetSoldEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PETSOLD_EVENT, log);
        PetSoldEventResponse typedResponse = new PetSoldEventResponse();
        typedResponse.log = log;
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<PetSoldEventResponse> petSoldEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPetSoldEventFromLog(log));
    }

    public Flowable<PetSoldEventResponse> petSoldEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PETSOLD_EVENT));
        return petSoldEventFlowable(filter);
    }

//    public static List<SaleCancelledEventResponse> getSaleCancelledEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SALECANCELLED_EVENT, transactionReceipt);
//        ArrayList<SaleCancelledEventResponse> responses = new ArrayList<SaleCancelledEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            SaleCancelledEventResponse typedResponse = new SaleCancelledEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static SaleCancelledEventResponse getSaleCancelledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SALECANCELLED_EVENT, log);
        SaleCancelledEventResponse typedResponse = new SaleCancelledEventResponse();
        typedResponse.log = log;
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<SaleCancelledEventResponse> saleCancelledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSaleCancelledEventFromLog(log));
    }

    public Flowable<SaleCancelledEventResponse> saleCancelledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SALECANCELLED_EVENT));
        return saleCancelledEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> buyPet(BigInteger tokenId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYPET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelSale(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_CANCELSALE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> listPetForSale(BigInteger tokenId, BigInteger price) {
        final Function function = new Function(
                FUNC_LISTPETFORSALE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> petNFT() {
        final Function function = new Function(FUNC_PETNFT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, Boolean>> petSales(BigInteger param0) {
        final Function function = new Function(FUNC_PETSALES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, Boolean>>(function,
                new Callable<Tuple3<String, BigInteger, Boolean>>() {
                    @Override
                    public Tuple3<String, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    @Deprecated
    public static PetMarket_abi load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PetMarket_abi(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PetMarket_abi load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PetMarket_abi(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PetMarket_abi load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PetMarket_abi(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PetMarket_abi load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PetMarket_abi(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PetMarket_abi> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _petNFTAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _petNFTAddress)));
        return deployRemoteCall(PetMarket_abi.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<PetMarket_abi> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _petNFTAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _petNFTAddress)));
        return deployRemoteCall(PetMarket_abi.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PetMarket_abi> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _petNFTAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _petNFTAddress)));
        return deployRemoteCall(PetMarket_abi.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PetMarket_abi> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _petNFTAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _petNFTAddress)));
        return deployRemoteCall(PetMarket_abi.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

//    public static void linkLibraries(List<Contract.LinkReference> references) {
//        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
//    }

    public static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class PetListedEventResponse extends BaseEventResponse {
        public BigInteger tokenId;

        public String seller;

        public BigInteger price;
    }

    public static class PetSoldEventResponse extends BaseEventResponse {
        public BigInteger tokenId;

        public String buyer;

        public BigInteger price;
    }

    public static class SaleCancelledEventResponse extends BaseEventResponse {
        public BigInteger tokenId;
    }
}
