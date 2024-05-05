package com.uobfintech.nftpictures.abi;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class PetLottery_abi extends Contract {
    public static final String BINARY = "60c06040526402540be400600455348015610018575f80fd5b50604051611f34380380611f34833981810160405281019061003a9190610307565b8086868173ffffffffffffffffffffffffffffffffffffffff1660a08173ffffffffffffffffffffffffffffffffffffffff16815250508073ffffffffffffffffffffffffffffffffffffffff1660808173ffffffffffffffffffffffffffffffffffffffff168152505050505f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610117575f6040517f1e4fbdf700000000000000000000000000000000000000000000000000000000815260040161010e919061039f565b60405180910390fd5b6101268161018060201b60201c565b5083600281905550826003819055508160055f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050505050506103b8565b5f60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508160015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61027082610247565b9050919050565b61028081610266565b811461028a575f80fd5b50565b5f8151905061029b81610277565b92915050565b5f819050919050565b6102b3816102a1565b81146102bd575f80fd5b50565b5f815190506102ce816102aa565b92915050565b5f819050919050565b6102e6816102d4565b81146102f0575f80fd5b50565b5f81519050610301816102dd565b92915050565b5f805f805f8060c0878903121561032157610320610243565b5b5f61032e89828a0161028d565b965050602061033f89828a0161028d565b955050604061035089828a016102c0565b945050606061036189828a016102f3565b935050608061037289828a0161028d565b92505060a061038389828a0161028d565b9150509295509295509295565b61039981610266565b82525050565b5f6020820190506103b25f830184610390565b92915050565b60805160a051611b4d6103e75f395f81816101f80152610ea701525f81816102e80152610e6b0152611b4d5ff3fe608060405260043610610073575f3560e01c80638da5cb5b1161004d5780638da5cb5b146100e657806394985ddd14610110578063b37217a414610138578063f2fde38b146101685761007a565b8063715018a61461007c57806373a96d041461009257806378aa08ed146100bc5761007a565b3661007a57005b005b348015610087575f80fd5b50610090610190565b005b34801561009d575f80fd5b506100a66101a3565b6040516100b39190611098565b60405180910390f35b3480156100c7575f80fd5b506100d06101c8565b6040516100dd91906110c9565b60405180910390f35b3480156100f1575f80fd5b506100fa6101ce565b6040516101079190611102565b60405180910390f35b34801561011b575f80fd5b5061013660048036038101906101319190611189565b6101f6565b005b610152600480360381019061014d91906111c7565b610292565b60405161015f9190611201565b60405180910390f35b348015610173575f80fd5b5061018e60048036038101906101899190611244565b610488565b005b61019861050c565b6101a15f610593565b565b60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60045481565b5f60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610284576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161027b906112c9565b60405180910390fd5b61028e8282610656565b5050565b5f816004546102a19190611314565b3410156102e3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102da9061139f565b60405180910390fd5b6003547f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b815260040161033f9190611102565b602060405180830381865afa15801561035a573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061037e91906113d1565b10156103bf576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103b69061146c565b60405180910390fd5b6103cd600254600354610e68565b90503360065f8381526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508160075f8381526020019081526020015f2081905550803373ffffffffffffffffffffffffffffffffffffffff167f78d45e221ca4450c23810cead7feee81f5703304a5e653222f5cebf4d6d900d58460405161047b91906110c9565b60405180910390a3919050565b61049061050c565b5f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610500575f6040517f1e4fbdf70000000000000000000000000000000000000000000000000000000081526004016104f79190611102565b60405180910390fd5b61050981610593565b50565b610514610fab565b73ffffffffffffffffffffffffffffffffffffffff166105326101ce565b73ffffffffffffffffffffffffffffffffffffffff161461059157610555610fab565b6040517f118cdaa70000000000000000000000000000000000000000000000000000000081526004016105889190611102565b60405180910390fd5b565b5f60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508160015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b5f60075f8481526020019081526020015f205490505f60065f8581526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505f8267ffffffffffffffff8111156106ba576106b961148a565b5b6040519080825280602002602001820160405280156106e85781602001602082028036833780820191505090505b5090505f805b848110156109aa575f60016064888460405160200161070e9291906114b7565b604051602081830303815290604052805190602001205f1c610730919061150b565b61073a919061153b565b90505f6032821161074e576001905061076d565b6050821161076357600290506001935061076c565b60039050600193505b5b5f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772836040518263ffffffff1660e01b81526004016107c891906110c9565b5f60405180830381865afa1580156107e2573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f8201168201806040525081019061080a9190611691565b51896103e88661081a919061153b565b60405160200161082b9291906114b7565b604051602081830303815290604052805190602001205f1c61084d919061150b565b90505f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16637418b70b84846040518363ffffffff1660e01b81526004016108ac9291906114b7565b6020604051808303815f875af11580156108c8573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906108ec91906113d1565b905080878681518110610902576109016116d8565b5b60200260200101818152505060055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd308a846040518463ffffffff1660e01b815260040161096c93929190611705565b5f604051808303815f87803b158015610983575f80fd5b505af1158015610995573d5f803e3d5ffd5b505050505050505080806001019150506106ee565b50600a841480156109b9575080155b15610e12575f600a866103e96040516020016109d6929190611780565b604051602081830303815290604052805190602001205f1c6109f8919061150b565b90505f600290505f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772836040518263ffffffff1660e01b8152600401610a5a91906110c9565b5f60405180830381865afa158015610a74573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f82011682018060405250810190610a9c9190611691565b90505f815103610b8c576003915060055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772836040518263ffffffff1660e01b8152600401610b0491906110c9565b5f60405180830381865afa158015610b1e573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f82011682018060405250810190610b469190611691565b90505f815111610b8b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b82906117f1565b60405180910390fd5b5b5f8151896103ea604051602001610ba4929190611848565b604051602081830303815290604052805190602001205f1c610bc6919061150b565b90505f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16637418b70b85846040518363ffffffff1660e01b8152600401610c259291906114b7565b6020604051808303815f875af1158015610c41573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610c6591906113d1565b90505f878681518110610c7b57610c7a6116d8565b5b6020026020010151905060055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd8a30846040518463ffffffff1660e01b8152600401610ce393929190611705565b5f604051808303815f87803b158015610cfa575f80fd5b505af1158015610d0c573d5f803e3d5ffd5b5050505060055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd308b856040518463ffffffff1660e01b8152600401610d6e93929190611705565b5f604051808303815f87803b158015610d85575f80fd5b505af1158015610d97573d5f803e3d5ffd5b505050508873ffffffffffffffffffffffffffffffffffffffff167f44415a749c26a3958dd726e62425f42081cce4649781be4553ea9d6ca61153088383604051610de39291906114b7565b60405180910390a281888781518110610dff57610dfe6116d8565b5b6020026020010181815250505050505050505b8273ffffffffffffffffffffffffffffffffffffffff167fae70af528670c95920a68ff91ec77ac7eb73759dffa95a00ee2f50fcc23ac93d83604051610e589190611926565b60405180910390a2505050505050565b5f7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff16634000aea07f000000000000000000000000000000000000000000000000000000000000000084865f604051602001610eda929190611946565b6040516020818303038152906040526040518463ffffffff1660e01b8152600401610f07939291906119cd565b6020604051808303815f875af1158015610f23573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610f479190611a3e565b505f610f65845f305f808981526020019081526020015f2054610fb2565b905060015f808681526020019081526020015f2054610f84919061153b565b5f808681526020019081526020015f2081905550610fa28482610fec565b91505092915050565b5f33905090565b5f84848484604051602001610fca9493929190611a69565b604051602081830303815290604052805190602001205f1c9050949350505050565b5f8282604051602001611000929190611aec565b60405160208183030381529060405280519060200120905092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f819050919050565b5f61106061105b6110568461101e565b61103d565b61101e565b9050919050565b5f61107182611046565b9050919050565b5f61108282611067565b9050919050565b61109281611078565b82525050565b5f6020820190506110ab5f830184611089565b92915050565b5f819050919050565b6110c3816110b1565b82525050565b5f6020820190506110dc5f8301846110ba565b92915050565b5f6110ec8261101e565b9050919050565b6110fc816110e2565b82525050565b5f6020820190506111155f8301846110f3565b92915050565b5f604051905090565b5f80fd5b5f80fd5b5f819050919050565b61113e8161112c565b8114611148575f80fd5b50565b5f8135905061115981611135565b92915050565b611168816110b1565b8114611172575f80fd5b50565b5f813590506111838161115f565b92915050565b5f806040838503121561119f5761119e611124565b5b5f6111ac8582860161114b565b92505060206111bd85828601611175565b9150509250929050565b5f602082840312156111dc576111db611124565b5b5f6111e984828501611175565b91505092915050565b6111fb8161112c565b82525050565b5f6020820190506112145f8301846111f2565b92915050565b611223816110e2565b811461122d575f80fd5b50565b5f8135905061123e8161121a565b92915050565b5f6020828403121561125957611258611124565b5b5f61126684828501611230565b91505092915050565b5f82825260208201905092915050565b7f4f6e6c7920565246436f6f7264696e61746f722063616e2066756c66696c6c005f82015250565b5f6112b3601f8361126f565b91506112be8261127f565b602082019050919050565b5f6020820190508181035f8301526112e0816112a7565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61131e826110b1565b9150611329836110b1565b9250828202611337816110b1565b9150828204841483151761134e5761134d6112e7565b5b5092915050565b7f4e6f7420656e6f7567682065746865722073656e7400000000000000000000005f82015250565b5f61138960158361126f565b915061139482611355565b602082019050919050565b5f6020820190508181035f8301526113b68161137d565b9050919050565b5f815190506113cb8161115f565b92915050565b5f602082840312156113e6576113e5611124565b5b5f6113f3848285016113bd565b91505092915050565b7f4e6f7420656e6f756768204c494e4b202d2066696c6c20636f6e7472616374205f8201527f77697468204c494e4b0000000000000000000000000000000000000000000000602082015250565b5f61145660298361126f565b9150611461826113fc565b604082019050919050565b5f6020820190508181035f8301526114838161144a565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b5f6040820190506114ca5f8301856110ba565b6114d760208301846110ba565b9392505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601260045260245ffd5b5f611515826110b1565b9150611520836110b1565b9250826115305761152f6114de565b5b828206905092915050565b5f611545826110b1565b9150611550836110b1565b9250828201905080821115611568576115676112e7565b5b92915050565b5f80fd5b5f601f19601f8301169050919050565b61158b82611572565b810181811067ffffffffffffffff821117156115aa576115a961148a565b5b80604052505050565b5f6115bc61111b565b90506115c88282611582565b919050565b5f67ffffffffffffffff8211156115e7576115e661148a565b5b602082029050602081019050919050565b5f80fd5b5f61160e611609846115cd565b6115b3565b90508083825260208201905060208402830185811115611631576116306115f8565b5b835b8181101561165a578061164688826113bd565b845260208401935050602081019050611633565b5050509392505050565b5f82601f8301126116785761167761156e565b5b81516116888482602086016115fc565b91505092915050565b5f602082840312156116a6576116a5611124565b5b5f82015167ffffffffffffffff8111156116c3576116c2611128565b5b6116cf84828501611664565b91505092915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b5f6060820190506117185f8301866110f3565b61172560208301856110f3565b61173260408301846110ba565b949350505050565b5f819050919050565b5f61ffff82169050919050565b5f61176a6117656117608461173a565b61103d565b611743565b9050919050565b61177a81611750565b82525050565b5f6040820190506117935f8301856110ba565b6117a06020830184611771565b9392505050565b7f4e6f206c6576656c2033207065747320617661696c61626c65000000000000005f82015250565b5f6117db60198361126f565b91506117e6826117a7565b602082019050919050565b5f6020820190508181035f830152611808816117cf565b9050919050565b5f819050919050565b5f61183261182d6118288461180f565b61103d565b611743565b9050919050565b61184281611818565b82525050565b5f60408201905061185b5f8301856110ba565b6118686020830184611839565b9392505050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b6118a1816110b1565b82525050565b5f6118b28383611898565b60208301905092915050565b5f602082019050919050565b5f6118d48261186f565b6118de8185611879565b93506118e983611889565b805f5b8381101561191957815161190088826118a7565b975061190b836118be565b9250506001810190506118ec565b5085935050505092915050565b5f6020820190508181035f83015261193e81846118ca565b905092915050565b5f6040820190506119595f8301856111f2565b61196660208301846110ba565b9392505050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f61199f8261196d565b6119a98185611977565b93506119b9818560208601611987565b6119c281611572565b840191505092915050565b5f6060820190506119e05f8301866110f3565b6119ed60208301856110ba565b81810360408301526119ff8184611995565b9050949350505050565b5f8115159050919050565b611a1d81611a09565b8114611a27575f80fd5b50565b5f81519050611a3881611a14565b92915050565b5f60208284031215611a5357611a52611124565b5b5f611a6084828501611a2a565b91505092915050565b5f608082019050611a7c5f8301876111f2565b611a8960208301866110ba565b611a9660408301856110f3565b611aa360608301846110ba565b95945050505050565b5f819050919050565b611ac6611ac18261112c565b611aac565b82525050565b5f819050919050565b611ae6611ae1826110b1565b611acc565b82525050565b5f611af78285611ab5565b602082019150611b078284611ad5565b602082019150819050939250505056fea26469706673582212200fc472cae6f3e4ce31332d80ff8295bb66596870e02eebfd7e1fd9d036fa274164736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_GETRANDOMNUMBER = "getRandomNumber";

    public static final String FUNC_LOTTERYFEE = "lotteryFee";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PETNFTCONTRACT = "petNFTContract";

    public static final String FUNC_RAWFULFILLRANDOMNESS = "rawFulfillRandomness";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event HIGHLEVELPETENSURED_EVENT = new Event("HighLevelPetEnsured", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOTTERYFULFILLED_EVENT = new Event("LotteryFulfilled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    public static final Event LOTTERYREQUESTED_EVENT = new Event("LotteryRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected PetLottery_abi(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PetLottery_abi(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PetLottery_abi(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PetLottery_abi(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<HighLevelPetEnsuredEventResponse> getHighLevelPetEnsuredEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(HIGHLEVELPETENSURED_EVENT, transactionReceipt);
//        ArrayList<HighLevelPetEnsuredEventResponse> responses = new ArrayList<HighLevelPetEnsuredEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            HighLevelPetEnsuredEventResponse typedResponse = new HighLevelPetEnsuredEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.highLevelTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.replacedTokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static HighLevelPetEnsuredEventResponse getHighLevelPetEnsuredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(HIGHLEVELPETENSURED_EVENT, log);
        HighLevelPetEnsuredEventResponse typedResponse = new HighLevelPetEnsuredEventResponse();
        typedResponse.log = log;
        typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.highLevelTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.replacedTokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<HighLevelPetEnsuredEventResponse> highLevelPetEnsuredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getHighLevelPetEnsuredEventFromLog(log));
    }

    public Flowable<HighLevelPetEnsuredEventResponse> highLevelPetEnsuredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HIGHLEVELPETENSURED_EVENT));
        return highLevelPetEnsuredEventFlowable(filter);
    }

//    public static List<LotteryFulfilledEventResponse> getLotteryFulfilledEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOTTERYFULFILLED_EVENT, transactionReceipt);
//        ArrayList<LotteryFulfilledEventResponse> responses = new ArrayList<LotteryFulfilledEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            LotteryFulfilledEventResponse typedResponse = new LotteryFulfilledEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
//            //typedResponse.tokenIds = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static LotteryFulfilledEventResponse getLotteryFulfilledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOTTERYFULFILLED_EVENT, log);
        LotteryFulfilledEventResponse typedResponse = new LotteryFulfilledEventResponse();
        typedResponse.log = log;
        typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
        //typedResponse.tokenIds = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
        return typedResponse;
    }

    public Flowable<LotteryFulfilledEventResponse> lotteryFulfilledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLotteryFulfilledEventFromLog(log));
    }

    public Flowable<LotteryFulfilledEventResponse> lotteryFulfilledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOTTERYFULFILLED_EVENT));
        return lotteryFulfilledEventFlowable(filter);
    }

//    public static List<LotteryRequestedEventResponse> getLotteryRequestedEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOTTERYREQUESTED_EVENT, transactionReceipt);
//        ArrayList<LotteryRequestedEventResponse> responses = new ArrayList<LotteryRequestedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            LotteryRequestedEventResponse typedResponse = new LotteryRequestedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.requestId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
//            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static LotteryRequestedEventResponse getLotteryRequestedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOTTERYREQUESTED_EVENT, log);
        LotteryRequestedEventResponse typedResponse = new LotteryRequestedEventResponse();
        typedResponse.log = log;
        typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.requestId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<LotteryRequestedEventResponse> lotteryRequestedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLotteryRequestedEventFromLog(log));
    }

    public Flowable<LotteryRequestedEventResponse> lotteryRequestedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOTTERYREQUESTED_EVENT));
        return lotteryRequestedEventFlowable(filter);
    }

//    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
//        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> getRandomNumber(BigInteger amount, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_GETRANDOMNUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> lotteryFee() {
        final Function function = new Function(FUNC_LOTTERYFEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> petNFTContract() {
        final Function function = new Function(FUNC_PETNFTCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> rawFulfillRandomness(byte[] requestId, BigInteger randomness) {
        final Function function = new Function(
                FUNC_RAWFULFILLRANDOMNESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(requestId), 
                new org.web3j.abi.datatypes.generated.Uint256(randomness)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PetLottery_abi load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PetLottery_abi(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PetLottery_abi load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PetLottery_abi(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PetLottery_abi load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PetLottery_abi(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PetLottery_abi load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PetLottery_abi(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PetLottery_abi> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _vrfCoordinator, String _linkToken, byte[] _keyHash, BigInteger _fee, String _petNFTContractAddress, String initialOwner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _vrfCoordinator), 
                new org.web3j.abi.datatypes.Address(160, _linkToken), 
                new org.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_fee), 
                new org.web3j.abi.datatypes.Address(160, _petNFTContractAddress), 
                new org.web3j.abi.datatypes.Address(160, initialOwner)));
        return deployRemoteCall(PetLottery_abi.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<PetLottery_abi> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _vrfCoordinator, String _linkToken, byte[] _keyHash, BigInteger _fee, String _petNFTContractAddress, String initialOwner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _vrfCoordinator), 
                new org.web3j.abi.datatypes.Address(160, _linkToken), 
                new org.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_fee), 
                new org.web3j.abi.datatypes.Address(160, _petNFTContractAddress), 
                new org.web3j.abi.datatypes.Address(160, initialOwner)));
        return deployRemoteCall(PetLottery_abi.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PetLottery_abi> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _vrfCoordinator, String _linkToken, byte[] _keyHash, BigInteger _fee, String _petNFTContractAddress, String initialOwner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _vrfCoordinator), 
                new org.web3j.abi.datatypes.Address(160, _linkToken), 
                new org.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_fee), 
                new org.web3j.abi.datatypes.Address(160, _petNFTContractAddress), 
                new org.web3j.abi.datatypes.Address(160, initialOwner)));
        return deployRemoteCall(PetLottery_abi.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PetLottery_abi> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _vrfCoordinator, String _linkToken, byte[] _keyHash, BigInteger _fee, String _petNFTContractAddress, String initialOwner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _vrfCoordinator), 
                new org.web3j.abi.datatypes.Address(160, _linkToken), 
                new org.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_fee), 
                new org.web3j.abi.datatypes.Address(160, _petNFTContractAddress), 
                new org.web3j.abi.datatypes.Address(160, initialOwner)));
        return deployRemoteCall(PetLottery_abi.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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

    public static class HighLevelPetEnsuredEventResponse extends BaseEventResponse {
        public String requester;

        public BigInteger highLevelTokenId;

        public BigInteger replacedTokenId;
    }

    public static class LotteryFulfilledEventResponse extends BaseEventResponse {
        public String requester;

        public List<BigInteger> tokenIds;
    }

    public static class LotteryRequestedEventResponse extends BaseEventResponse {
        public String requester;

        public byte[] requestId;

        public BigInteger amount;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
