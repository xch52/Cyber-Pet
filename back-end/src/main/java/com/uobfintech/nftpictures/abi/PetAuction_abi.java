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
import org.web3j.abi.datatypes.DynamicArray;
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
import org.web3j.tuples.generated.Tuple8;
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
public class PetAuction_abi extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b50604051611bd9380380611bd9833981810160405281019061003191906100dc565b60015f819055508060035f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050610107565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6100ab82610082565b9050919050565b6100bb816100a1565b81146100c5575f80fd5b50565b5f815190506100d6816100b2565b92915050565b5f602082840312156100f1576100f061007e565b5b5f6100fe848285016100c8565b91505092915050565b611ac5806101145f395ff3fe608060405260043610610054575f3560e01c8063454a2ab314610058578063571a26a0146100745780636dd98867146100b7578063778cdf3d146100e1578063b9a2de3a1461010b578063cda4beef14610133575b5f80fd5b610072600480360381019061006d9190611072565b61015b565b005b34801561007f575f80fd5b5061009a60048036038101906100959190611072565b61044a565b6040516100ae989796959493929190611105565b60405180910390f35b3480156100c2575f80fd5b506100cb6104d8565b6040516100d891906111dc565b60405180910390f35b3480156100ec575f80fd5b506100f56104fd565b60405161010291906112ac565b60405180910390f35b348015610116575f80fd5b50610131600480360381019061012c9190611072565b61050e565b005b34801561013e575f80fd5b50610159600480360381019061015491906112cc565b610920565b005b610163610dbc565b5f60045f8381526020019081526020015f209050806007015f9054906101000a900460ff166101c7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101be90611376565b60405180910390fd5b806002015442101580156101df575080600301544211155b61021e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610215906113de565b60405180910390fd5b8060060154341015610265576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161025c9061146c565b60405180910390fd5b806004015434116102ab576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102a2906114fa565b60405180910390fd5b5f816005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505f8260040154905034836004018190555033836005015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614610402575f8273ffffffffffffffffffffffffffffffffffffffff168260405161037d90611545565b5f6040518083038185875af1925050503d805f81146103b7576040519150601f19603f3d011682016040523d82523d5f602084013e6103bc565b606091505b5050905080610400576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103f7906115a3565b60405180910390fd5b505b837f0e54eff26401bf69b81b26f60bd85ef47f5d85275c1d268d84f68d6897431c4733346040516104349291906115c1565b60405180910390a2505050610447610e09565b50565b6004602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015490806002015490806003015490806004015490806005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806006015490806007015f9054906101000a900460ff16905088565b60035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60606105096001610e12565b905090565b610516610dbc565b5f60045f8381526020019081526020015f209050806007015f9054906101000a900460ff1661057a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161057190611376565b60405180910390fd5b806003015442116105c0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105b790611632565b60405180910390fd5b805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16148061066a5750806005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b6106a9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106a0906116c0565b60405180910390fd5b5f816007015f6101000a81548160ff02191690831515021790555080600601548160040154106107f05760035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd30836005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16856040518463ffffffff1660e01b8152600401610754939291906116de565b5f604051808303815f87803b15801561076b575f80fd5b505af115801561077d573d5f803e3d5ffd5b50505050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc826004015490811502906040515f60405180830381858888f193505050501580156107ea573d5f803e3d5ffd5b5061089e565b60035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd30835f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16856040518463ffffffff1660e01b8152600401610870939291906116de565b5f604051808303815f87803b158015610887575f80fd5b505af1158015610899573d5f803e3d5ffd5b505050505b6108b2826001610e3190919063ffffffff16565b50817fd2aa34a4fdbbc6dff6a3e56f46e0f3ae2a31d7785ff3487aa5c95c642acea501826005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16836004015460405161090c9291906115c1565b60405180910390a25061091d610e09565b50565b3373ffffffffffffffffffffffffffffffffffffffff1660035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16636352211e856040518263ffffffff1660e01b81526004016109919190611713565b602060405180830381865afa1580156109ac573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906109d09190611756565b73ffffffffffffffffffffffffffffffffffffffff1614610a26576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a1d906117f1565b60405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff1660035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663081812fc856040518263ffffffff1660e01b8152600401610a979190611713565b602060405180830381865afa158015610ab2573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610ad69190611756565b73ffffffffffffffffffffffffffffffffffffffff1614610b2c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b239061187f565b60405180910390fd5b5f8211610b6e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b65906118e7565b60405180910390fd5b5f4290505f8282610b7f9190611932565b90506040518061010001604052803373ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018381526020018281526020015f81526020015f73ffffffffffffffffffffffffffffffffffffffff1681526020018581526020016001151581525060045f8781526020019081526020015f205f820151815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060c0820151816006015560e0820151816007015f6101000a81548160ff021916908315150217905550905050610ced856001610e4890919063ffffffff16565b5060035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3330886040518463ffffffff1660e01b8152600401610d4c939291906116de565b5f604051808303815f87803b158015610d63575f80fd5b505af1158015610d75573d5f803e3d5ffd5b50505050847fa9c8dfcda5664a5a124c713e386da27de87432d5b668e79458501eb296389ba7858484604051610dad93929190611965565b60405180910390a25050505050565b60025f5403610e00576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610df7906119e4565b60405180910390fd5b60025f81905550565b60015f81905550565b60605f610e20835f01610e5f565b905060608190508092505050919050565b5f610e40835f01835f1b610eb8565b905092915050565b5f610e57835f01835f1b610fb4565b905092915050565b6060815f01805480602002602001604051908101604052809291908181526020018280548015610eac57602002820191905f5260205f20905b815481526020019060010190808311610e98575b50505050509050919050565b5f80836001015f8481526020019081526020015f205490505f8114610fa9575f600182610ee59190611a02565b90505f6001865f0180549050610efb9190611a02565b9050808214610f61575f865f018281548110610f1a57610f19611a35565b5b905f5260205f200154905080875f018481548110610f3b57610f3a611a35565b5b905f5260205f20018190555083876001015f8381526020019081526020015f2081905550505b855f01805480610f7457610f73611a62565b5b600190038181905f5260205f20015f90559055856001015f8681526020019081526020015f205f905560019350505050610fae565b5f9150505b92915050565b5f610fbf838361101b565b61101157825f0182908060018154018082558091505060019003905f5260205f20015f9091909190915055825f0180549050836001015f8481526020019081526020015f208190555060019050611015565b5f90505b92915050565b5f80836001015f8481526020019081526020015f20541415905092915050565b5f80fd5b5f819050919050565b6110518161103f565b811461105b575f80fd5b50565b5f8135905061106c81611048565b92915050565b5f602082840312156110875761108661103b565b5b5f6110948482850161105e565b91505092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6110c68261109d565b9050919050565b6110d6816110bc565b82525050565b6110e58161103f565b82525050565b5f8115159050919050565b6110ff816110eb565b82525050565b5f610100820190506111195f83018b6110cd565b611126602083018a6110dc565b61113360408301896110dc565b61114060608301886110dc565b61114d60808301876110dc565b61115a60a08301866110cd565b61116760c08301856110dc565b61117460e08301846110f6565b9998505050505050505050565b5f819050919050565b5f6111a461119f61119a8461109d565b611181565b61109d565b9050919050565b5f6111b58261118a565b9050919050565b5f6111c6826111ab565b9050919050565b6111d6816111bc565b82525050565b5f6020820190506111ef5f8301846111cd565b92915050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b6112278161103f565b82525050565b5f611238838361121e565b60208301905092915050565b5f602082019050919050565b5f61125a826111f5565b61126481856111ff565b935061126f8361120f565b805f5b8381101561129f578151611286888261122d565b975061129183611244565b925050600181019050611272565b5085935050505092915050565b5f6020820190508181035f8301526112c48184611250565b905092915050565b5f805f606084860312156112e3576112e261103b565b5b5f6112f08682870161105e565b93505060206113018682870161105e565b92505060406113128682870161105e565b9150509250925092565b5f82825260208201905092915050565b7f41756374696f6e206973206e6f742061637469766500000000000000000000005f82015250565b5f61136060158361131c565b915061136b8261132c565b602082019050919050565b5f6020820190508181035f83015261138d81611354565b9050919050565b7f41756374696f6e206973206e6f7420696e2061637469766520706572696f64005f82015250565b5f6113c8601f8361131c565b91506113d382611394565b602082019050919050565b5f6020820190508181035f8301526113f5816113bc565b9050919050565b7f426964206d757374206265206174206c656173742074686520726573657276655f8201527f2070726963650000000000000000000000000000000000000000000000000000602082015250565b5f61145660268361131c565b9150611461826113fc565b604082019050919050565b5f6020820190508181035f8301526114838161144a565b9050919050565b7f546865726520697320616c7265616479206120686967686572206f72206571755f8201527f616c206269640000000000000000000000000000000000000000000000000000602082015250565b5f6114e460268361131c565b91506114ef8261148a565b604082019050919050565b5f6020820190508181035f830152611511816114d8565b9050919050565b5f81905092915050565b50565b5f6115305f83611518565b915061153b82611522565b5f82019050919050565b5f61154f82611525565b9150819050919050565b7f526566756e64206661696c6564000000000000000000000000000000000000005f82015250565b5f61158d600d8361131c565b915061159882611559565b602082019050919050565b5f6020820190508181035f8301526115ba81611581565b9050919050565b5f6040820190506115d45f8301856110cd565b6115e160208301846110dc565b9392505050565b7f41756374696f6e20686173206e6f7420656e64656420796574000000000000005f82015250565b5f61161c60198361131c565b9150611627826115e8565b602082019050919050565b5f6020820190508181035f83015261164981611610565b9050919050565b7f4f6e6c792073656c6c6572206f722068696768657374206269646465722063615f8201527f6e20656e64207468652061756374696f6e000000000000000000000000000000602082015250565b5f6116aa60318361131c565b91506116b582611650565b604082019050919050565b5f6020820190508181035f8301526116d78161169e565b9050919050565b5f6060820190506116f15f8301866110cd565b6116fe60208301856110cd565b61170b60408301846110dc565b949350505050565b5f6020820190506117265f8301846110dc565b92915050565b611735816110bc565b811461173f575f80fd5b50565b5f815190506117508161172c565b92915050565b5f6020828403121561176b5761176a61103b565b5b5f61177884828501611742565b91505092915050565b7f4f6e6c7920746865206f776e65722063616e2063726561746520616e206175635f8201527f74696f6e00000000000000000000000000000000000000000000000000000000602082015250565b5f6117db60248361131c565b91506117e682611781565b604082019050919050565b5f6020820190508181035f830152611808816117cf565b9050919050565b7f436f6e7472616374206e6565647320746f20626520617070726f76656420746f5f8201527f207472616e73666572204e465400000000000000000000000000000000000000602082015250565b5f611869602d8361131c565b91506118748261180f565b604082019050919050565b5f6020820190508181035f8301526118968161185d565b9050919050565b7f526573657276655072696365206d757374206d6f7265207468616e20300000005f82015250565b5f6118d1601d8361131c565b91506118dc8261189d565b602082019050919050565b5f6020820190508181035f8301526118fe816118c5565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61193c8261103f565b91506119478361103f565b925082820190508082111561195f5761195e611905565b5b92915050565b5f6060820190506119785f8301866110dc565b61198560208301856110dc565b61199260408301846110dc565b949350505050565b7f5265656e7472616e637947756172643a207265656e7472616e742063616c6c005f82015250565b5f6119ce601f8361131c565b91506119d98261199a565b602082019050919050565b5f6020820190508181035f8301526119fb816119c2565b9050919050565b5f611a0c8261103f565b9150611a178361103f565b9250828203905081811115611a2f57611a2e611905565b5b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603160045260245ffdfea2646970667358221220b101cb74ebb338f8cb11878e82f06087a7fa0e97bcb3ae9dc574b925cb5f4f3c64736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_AUCTIONS = "auctions";

    public static final String FUNC_BID = "bid";

    public static final String FUNC_CREATEAUCTION = "createAuction";

    public static final String FUNC_ENDAUCTION = "endAuction";

    public static final String FUNC_LISTACTIVEAUCTIONS = "listActiveAuctions";

    public static final String FUNC_PETNFT = "petNFT";

    public static final Event AUCTIONCREATED_EVENT = new Event("AuctionCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONENDED_EVENT = new Event("AuctionEnded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BIDPLACED_EVENT = new Event("BidPlaced", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected PetAuction_abi(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PetAuction_abi(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PetAuction_abi(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PetAuction_abi(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<AuctionCreatedEventResponse> getAuctionCreatedEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AUCTIONCREATED_EVENT, transactionReceipt);
//        ArrayList<AuctionCreatedEventResponse> responses = new ArrayList<AuctionCreatedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.reservePrice = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//            typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static AuctionCreatedEventResponse getAuctionCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AUCTIONCREATED_EVENT, log);
        AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.reservePrice = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.startTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAuctionCreatedEventFromLog(log));
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONCREATED_EVENT));
        return auctionCreatedEventFlowable(filter);
    }

//    public static List<AuctionEndedEventResponse> getAuctionEndedEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AUCTIONENDED_EVENT, transactionReceipt);
//        ArrayList<AuctionEndedEventResponse> responses = new ArrayList<AuctionEndedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.winner = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static AuctionEndedEventResponse getAuctionEndedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AUCTIONENDED_EVENT, log);
        AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
        typedResponse.log = log;
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.winner = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAuctionEndedEventFromLog(log));
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONENDED_EVENT));
        return auctionEndedEventFlowable(filter);
    }

//    public static List<BidPlacedEventResponse> getBidPlacedEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BIDPLACED_EVENT, transactionReceipt);
//        ArrayList<BidPlacedEventResponse> responses = new ArrayList<BidPlacedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            BidPlacedEventResponse typedResponse = new BidPlacedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static BidPlacedEventResponse getBidPlacedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BIDPLACED_EVENT, log);
        BidPlacedEventResponse typedResponse = new BidPlacedEventResponse();
        typedResponse.log = log;
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<BidPlacedEventResponse> bidPlacedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBidPlacedEventFromLog(log));
    }

    public Flowable<BidPlacedEventResponse> bidPlacedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BIDPLACED_EVENT));
        return bidPlacedEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean>> auctions(BigInteger param0) {
        final Function function = new Function(FUNC_AUCTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean>>(function,
                new Callable<Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean>>() {
                    @Override
                    public Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, BigInteger, BigInteger, BigInteger, BigInteger, String, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (Boolean) results.get(7).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> bid(BigInteger tokenId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> createAuction(BigInteger tokenId, BigInteger reservePrice, BigInteger auctionDuration) {
        final Function function = new Function(
                FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.generated.Uint256(reservePrice), 
                new org.web3j.abi.datatypes.generated.Uint256(auctionDuration)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> endAuction(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_ENDAUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> listActiveAuctions() {
        final Function function = new Function(FUNC_LISTACTIVEAUCTIONS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> petNFT() {
        final Function function = new Function(FUNC_PETNFT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static PetAuction_abi load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PetAuction_abi(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PetAuction_abi load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PetAuction_abi(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PetAuction_abi load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PetAuction_abi(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PetAuction_abi load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PetAuction_abi(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PetAuction_abi> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _petNFTAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _petNFTAddress)));
        return deployRemoteCall(PetAuction_abi.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<PetAuction_abi> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _petNFTAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _petNFTAddress)));
        return deployRemoteCall(PetAuction_abi.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PetAuction_abi> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _petNFTAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _petNFTAddress)));
        return deployRemoteCall(PetAuction_abi.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PetAuction_abi> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _petNFTAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _petNFTAddress)));
        return deployRemoteCall(PetAuction_abi.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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

    public static class AuctionCreatedEventResponse extends BaseEventResponse {
        public BigInteger tokenId;

        public BigInteger reservePrice;

        public BigInteger startTime;

        public BigInteger endTime;
    }

    public static class AuctionEndedEventResponse extends BaseEventResponse {
        public BigInteger tokenId;

        public String winner;

        public BigInteger amount;
    }

    public static class BidPlacedEventResponse extends BaseEventResponse {
        public BigInteger tokenId;

        public String bidder;

        public BigInteger amount;
    }
}
