package com.uobfintech.cyberpets.abi;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
import org.web3j.tuples.generated.Tuple2;
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
    public static final String BINARY = "608060405234801561000f575f80fd5b50604051611ddf380380611ddf833981810160405281019061003191906100dc565b60015f819055508060015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050610107565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6100ab82610082565b9050919050565b6100bb816100a1565b81146100c5575f80fd5b50565b5f815190506100d6816100b2565b92915050565b5f602082840312156100f1576100f061007e565b5b5f6100fe848285016100c8565b91505092915050565b611ccb806101145f395ff3fe60806040526004361061006f575f3560e01c80638c746d8b1161004d5780638c746d8b146100f05780639edded111461012d578063bd94b00514610149578063ea58f56c146101715761006f565b8063112ab5481461007357806368a7ac5b1461009e5780636dd98867146100c6575b5f80fd5b34801561007e575f80fd5b506100876101af565b604051610095929190611177565b60405180910390f35b3480156100a9575f80fd5b506100c460048036038101906100bf91906111e7565b61037e565b005b3480156100d1575f80fd5b506100da6106a7565b6040516100e79190611280565b60405180910390f35b3480156100fb575f80fd5b5061011660048036038101906101119190611299565b6106cc565b604051610124929190611416565b60405180910390f35b61014760048036038101906101429190611299565b610875565b005b348015610154575f80fd5b5061016f600480360381019061016a9190611299565b610acc565b005b34801561017c575f80fd5b5061019760048036038101906101929190611299565b610c3c565b6040516101a693929190611471565b60405180910390f35b6060805f6101bd6002610c8d565b67ffffffffffffffff8111156101d6576101d56114a6565b5b6040519080825280602002602001820160405280156102045781602001602082028036833780820191505090505b5090505f6102126002610c8d565b67ffffffffffffffff81111561022b5761022a6114a6565b5b60405190808252806020026020018201604052801561026457816020015b610251610f0b565b8152602001906001900390816102495790505b5090505f5b6102736002610c8d565b811015610371575f61028f826002610ca090919063ffffffff16565b9050808483815181106102a5576102a46114d3565b5b60200260200101818152505060045f8281526020019081526020015f206040518060600160405290815f82015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152602001600282015f9054906101000a900460ff161515151581525050838381518110610358576103576114d3565b5b6020026020010181905250508080600101915050610269565b5081819350935050509091565b3373ffffffffffffffffffffffffffffffffffffffff1660015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16636352211e846040518263ffffffff1660e01b81526004016103ef9190611500565b602060405180830381865afa15801561040a573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061042e9190611543565b73ffffffffffffffffffffffffffffffffffffffff1614610484576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161047b906115ee565b60405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff1660015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663081812fc846040518263ffffffff1660e01b81526004016104f59190611500565b602060405180830381865afa158015610510573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906105349190611543565b73ffffffffffffffffffffffffffffffffffffffff161461058a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105819061167c565b60405180910390fd5b60405180606001604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018281526020016001151581525060045f8481526020019081526020015f205f820151815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101556040820151816002015f6101000a81548160ff021916908315150217905550905050610653826002610cb790919063ffffffff16565b503373ffffffffffffffffffffffffffffffffffffffff16827f187f616f90eaf716f9196a8f2eaead21fec5a107062159e6cc7e92b70ba9bca98360405161069b9190611500565b60405180910390a35050565b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6106d4610f0b565b6106dc610f41565b60045f8481526020019081526020015f206002015f9054906101000a900460ff1661073c576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107339061170a565b60405180910390fd5b60045f8481526020019081526020015f206040518060600160405290815f82015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152602001600282015f9054906101000a900460ff161515151581525050915060015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16633b6db7b5846040518263ffffffff1660e01b815260040161082c9190611500565b5f60405180830381865afa158015610846573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f8201168201806040525081019061086e919061195e565b9050915091565b61087d610cce565b610891816002610d1b90919063ffffffff16565b6108d0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108c7906119ef565b60405180910390fd5b5f60045f8381526020019081526020015f2090508060010154341461092a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161092190611a57565b60405180910390fd5b5f816002015f6101000a81548160ff021916908315150217905550610959826002610d3290919063ffffffff16565b5060015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166342842e0e825f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1633856040518463ffffffff1660e01b81526004016109da93929190611a75565b5f604051808303815f87803b1580156109f1575f80fd5b505af1158015610a03573d5f803e3d5ffd5b50505050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc3490811502906040515f60405180830381858888f19350505050158015610a6c573d5f803e3d5ffd5b503373ffffffffffffffffffffffffffffffffffffffff16827fea90122f42bcf76a252d2eb59092578d1f9c468eda2783848df33dce5bca8b558360010154604051610ab89190611500565b60405180910390a350610ac9610d49565b50565b3373ffffffffffffffffffffffffffffffffffffffff1660045f8381526020019081526020015f205f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610b6c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b6390611b1a565b60405180910390fd5b60045f8281526020019081526020015f206002015f9054906101000a900460ff16610bcc576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610bc390611b82565b60405180910390fd5b5f60045f8381526020019081526020015f206002015f6101000a81548160ff021916908315150217905550610c0b816002610d3290919063ffffffff16565b50807f2c56893f6f6026d19bd17b7d05c9f15c522de1ae2b1c3a825f91a73c799321f260405160405180910390a250565b6004602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015490806002015f9054906101000a900460ff16905083565b5f610c99825f01610d52565b9050919050565b5f610cad835f0183610d61565b5f1c905092915050565b5f610cc6835f01835f1b610d88565b905092915050565b60025f5403610d12576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d0990611bea565b60405180910390fd5b60025f81905550565b5f610d2a835f01835f1b610def565b905092915050565b5f610d41835f01835f1b610e0f565b905092915050565b60015f81905550565b5f815f01805490509050919050565b5f825f018281548110610d7757610d766114d3565b5b905f5260205f200154905092915050565b5f610d938383610def565b610de557825f0182908060018154018082558091505060019003905f5260205f20015f9091909190915055825f0180549050836001015f8481526020019081526020015f208190555060019050610de9565b5f90505b92915050565b5f80836001015f8481526020019081526020015f20541415905092915050565b5f80836001015f8481526020019081526020015f205490505f8114610f00575f600182610e3c9190611c35565b90505f6001865f0180549050610e529190611c35565b9050808214610eb8575f865f018281548110610e7157610e706114d3565b5b905f5260205f200154905080875f018481548110610e9257610e916114d3565b5b905f5260205f20018190555083876001015f8381526020019081526020015f2081905550505b855f01805480610ecb57610eca611c68565b5b600190038181905f5260205f20015f90559055856001015f8681526020019081526020015f205f905560019350505050610f05565b5f9150505b92915050565b60405180606001604052805f73ffffffffffffffffffffffffffffffffffffffff1681526020015f81526020015f151581525090565b6040518060c001604052805f815260200160608152602001606081526020016060815260200160608152602001606081525090565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f819050919050565b610fb181610f9f565b82525050565b5f610fc28383610fa8565b60208301905092915050565b5f602082019050919050565b5f610fe482610f76565b610fee8185610f80565b9350610ff983610f90565b805f5b838110156110295781516110108882610fb7565b975061101b83610fce565b925050600181019050610ffc565b5085935050505092915050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6110888261105f565b9050919050565b6110988161107e565b82525050565b5f8115159050919050565b6110b28161109e565b82525050565b606082015f8201516110cc5f85018261108f565b5060208201516110df6020850182610fa8565b5060408201516110f260408501826110a9565b50505050565b5f61110383836110b8565b60608301905092915050565b5f602082019050919050565b5f61112582611036565b61112f8185611040565b935061113a83611050565b805f5b8381101561116a57815161115188826110f8565b975061115c8361110f565b92505060018101905061113d565b5085935050505092915050565b5f6040820190508181035f83015261118f8185610fda565b905081810360208301526111a3818461111b565b90509392505050565b5f604051905090565b5f80fd5b5f80fd5b6111c681610f9f565b81146111d0575f80fd5b50565b5f813590506111e1816111bd565b92915050565b5f80604083850312156111fd576111fc6111b5565b5b5f61120a858286016111d3565b925050602061121b858286016111d3565b9150509250929050565b5f819050919050565b5f61124861124361123e8461105f565b611225565b61105f565b9050919050565b5f6112598261122e565b9050919050565b5f61126a8261124f565b9050919050565b61127a81611260565b82525050565b5f6020820190506112935f830184611271565b92915050565b5f602082840312156112ae576112ad6111b5565b5b5f6112bb848285016111d3565b91505092915050565b606082015f8201516112d85f85018261108f565b5060208201516112eb6020850182610fa8565b5060408201516112fe60408501826110a9565b50505050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f61134682611304565b611350818561130e565b935061136081856020860161131e565b6113698161132c565b840191505092915050565b5f60c083015f8301516113895f860182610fa8565b50602083015184820360208601526113a1828261133c565b915050604083015184820360408601526113bb828261133c565b915050606083015184820360608601526113d5828261133c565b915050608083015184820360808601526113ef828261133c565b91505060a083015184820360a0860152611409828261133c565b9150508091505092915050565b5f6080820190506114295f8301856112c4565b818103606083015261143b8184611374565b90509392505050565b61144d8161107e565b82525050565b61145c81610f9f565b82525050565b61146b8161109e565b82525050565b5f6060820190506114845f830186611444565b6114916020830185611453565b61149e6040830184611462565b949350505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b5f6020820190506115135f830184611453565b92915050565b6115228161107e565b811461152c575f80fd5b50565b5f8151905061153d81611519565b92915050565b5f60208284031215611558576115576111b5565b5b5f6115658482850161152f565b91505092915050565b5f82825260208201905092915050565b7f596f75206d757374206f776e207468652070657420746f206c697374206974205f8201527f666f722073616c65000000000000000000000000000000000000000000000000602082015250565b5f6115d860288361156e565b91506115e38261157e565b604082019050919050565b5f6020820190508181035f830152611605816115cc565b9050919050565b7f4d61726b6574206d75737420626520617070726f76656420746f207472616e735f8201527f6665722070657400000000000000000000000000000000000000000000000000602082015250565b5f61166660278361156e565b91506116718261160c565b604082019050919050565b5f6020820190508181035f8301526116938161165a565b9050919050565b7f5468697320706574206973206e6f742063757272656e746c7920666f722073615f8201527f6c652e0000000000000000000000000000000000000000000000000000000000602082015250565b5f6116f460238361156e565b91506116ff8261169a565b604082019050919050565b5f6020820190508181035f830152611721816116e8565b9050919050565b5f80fd5b6117358261132c565b810181811067ffffffffffffffff82111715611754576117536114a6565b5b80604052505050565b5f6117666111ac565b9050611772828261172c565b919050565b5f80fd5b5f81519050611789816111bd565b92915050565b5f80fd5b5f80fd5b5f67ffffffffffffffff8211156117b1576117b06114a6565b5b6117ba8261132c565b9050602081019050919050565b5f6117d96117d484611797565b61175d565b9050828152602081018484840111156117f5576117f4611793565b5b61180084828561131e565b509392505050565b5f82601f83011261181c5761181b61178f565b5b815161182c8482602086016117c7565b91505092915050565b5f60c0828403121561184a57611849611728565b5b61185460c061175d565b90505f6118638482850161177b565b5f83015250602082015167ffffffffffffffff81111561188657611885611777565b5b61189284828501611808565b602083015250604082015167ffffffffffffffff8111156118b6576118b5611777565b5b6118c284828501611808565b604083015250606082015167ffffffffffffffff8111156118e6576118e5611777565b5b6118f284828501611808565b606083015250608082015167ffffffffffffffff81111561191657611915611777565b5b61192284828501611808565b60808301525060a082015167ffffffffffffffff81111561194657611945611777565b5b61195284828501611808565b60a08301525092915050565b5f60208284031215611973576119726111b5565b5b5f82015167ffffffffffffffff8111156119905761198f6111b9565b5b61199c84828501611835565b91505092915050565b7f5468697320706574206973206e6f7420666f722073616c6500000000000000005f82015250565b5f6119d960188361156e565b91506119e4826119a5565b602082019050919050565b5f6020820190508181035f830152611a06816119cd565b9050919050565b7f496e73756666696369656e742066756e64732073656e740000000000000000005f82015250565b5f611a4160178361156e565b9150611a4c82611a0d565b602082019050919050565b5f6020820190508181035f830152611a6e81611a35565b9050919050565b5f606082019050611a885f830186611444565b611a956020830185611444565b611aa26040830184611453565b949350505050565b7f4f6e6c79207468652073656c6c65722063616e2063616e63656c2074686520735f8201527f616c650000000000000000000000000000000000000000000000000000000000602082015250565b5f611b0460238361156e565b9150611b0f82611aaa565b604082019050919050565b5f6020820190508181035f830152611b3181611af8565b9050919050565b7f53616c65206973206e6f742061637469766500000000000000000000000000005f82015250565b5f611b6c60128361156e565b9150611b7782611b38565b602082019050919050565b5f6020820190508181035f830152611b9981611b60565b9050919050565b7f5265656e7472616e637947756172643a207265656e7472616e742063616c6c005f82015250565b5f611bd4601f8361156e565b9150611bdf82611ba0565b602082019050919050565b5f6020820190508181035f830152611c0181611bc8565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f611c3f82610f9f565b9150611c4a83610f9f565b9250828203905081811115611c6257611c61611c08565b5b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603160045260245ffdfea2646970667358221220516ccd06fce01c8c6fb14b0c19e6ee60084a3f92ae77203d48ba52fd0eb598df64736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_BUYPET = "buyPet";

    public static final String FUNC_CANCELSALE = "cancelSale";

    public static final String FUNC_GETSALEDETAILS = "getSaleDetails";

    public static final String FUNC_LISTACTIVESALES = "listActiveSales";

    public static final String FUNC_LISTPETFORSALE = "listPetForSale";

    public static final String FUNC_PETNFT = "petNFT";

    public static final String FUNC_PETSALES = "petSales";

    public static final Event PETLISTED_EVENT = new Event("PetListed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PETSOLD_EVENT = new Event("PetSold", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
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
        if (eventValues.getNonIndexedValues().size() > 1){
            typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        }
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

    public RemoteFunctionCall<Tuple2<Sale, PetAttributes>> getSaleDetails(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETSALEDETAILS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Sale>() {}, new TypeReference<PetAttributes>() {}));
        return new RemoteFunctionCall<Tuple2<Sale, PetAttributes>>(function,
                new Callable<Tuple2<Sale, PetAttributes>>() {
                    @Override
                    public Tuple2<Sale, PetAttributes> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Sale, PetAttributes>(
                                (Sale) results.get(0), 
                                (PetAttributes) results.get(1));
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<List<BigInteger>, List<Sale>>> listActiveSales() {
        final Function function = new Function(FUNC_LISTACTIVESALES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Sale>>() {}));
        return new RemoteFunctionCall<Tuple2<List<BigInteger>, List<Sale>>>(function,
                new Callable<Tuple2<List<BigInteger>, List<Sale>>>() {
                    @Override
                    public Tuple2<List<BigInteger>, List<Sale>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<List<BigInteger>, List<Sale>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Sale>) results.get(1).getValue()));
                    }
                });
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

    public static class Sale extends StaticStruct {
        public String seller;

        public BigInteger price;

        public Boolean isActive;

        public Sale(String seller, BigInteger price, Boolean isActive) {
            super(new org.web3j.abi.datatypes.Address(160, seller), 
                    new org.web3j.abi.datatypes.generated.Uint256(price), 
                    new org.web3j.abi.datatypes.Bool(isActive));
            this.seller = seller;
            this.price = price;
            this.isActive = isActive;
        }

        public Sale(Address seller, Uint256 price, Bool isActive) {
            super(seller, price, isActive);
            this.seller = seller.getValue();
            this.price = price.getValue();
            this.isActive = isActive.getValue();
        }
    }

    public static class PetAttributes extends DynamicStruct {
        public BigInteger level;

        public String name;

        public String appearance;

        public String character;

        public String description;

        public String uri;

        public PetAttributes(BigInteger level, String name, String appearance, String character, String description, String uri) {
            super(new org.web3j.abi.datatypes.generated.Uint256(level), 
                    new org.web3j.abi.datatypes.Utf8String(name), 
                    new org.web3j.abi.datatypes.Utf8String(appearance), 
                    new org.web3j.abi.datatypes.Utf8String(character), 
                    new org.web3j.abi.datatypes.Utf8String(description), 
                    new org.web3j.abi.datatypes.Utf8String(uri));
            this.level = level;
            this.name = name;
            this.appearance = appearance;
            this.character = character;
            this.description = description;
            this.uri = uri;
        }

        public PetAttributes(Uint256 level, Utf8String name, Utf8String appearance, Utf8String character, Utf8String description, Utf8String uri) {
            super(level, name, appearance, character, description, uri);
            this.level = level.getValue();
            this.name = name.getValue();
            this.appearance = appearance.getValue();
            this.character = character.getValue();
            this.description = description.getValue();
            this.uri = uri.getValue();
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

        public BigInteger endTime;
    }

    public static class SaleCancelledEventResponse extends BaseEventResponse {
        public BigInteger tokenId;
    }
}
