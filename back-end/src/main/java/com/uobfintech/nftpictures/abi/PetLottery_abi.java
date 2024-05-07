package com.uobfintech.nftpictures.abi;

import io.reactivex.Flowable;
import java.math.BigInteger;
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
    public static final String BINARY = "60c0604052600a600455348015610014575f80fd5b5060405161214f38038061214f83398181016040528101906100369190610303565b8086868173ffffffffffffffffffffffffffffffffffffffff1660a08173ffffffffffffffffffffffffffffffffffffffff16815250508073ffffffffffffffffffffffffffffffffffffffff1660808173ffffffffffffffffffffffffffffffffffffffff168152505050505f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610113575f6040517f1e4fbdf700000000000000000000000000000000000000000000000000000000815260040161010a919061039b565b60405180910390fd5b6101228161017c60201b60201c565b5083600281905550826003819055508160055f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050505050506103b4565b5f60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508160015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61026c82610243565b9050919050565b61027c81610262565b8114610286575f80fd5b50565b5f8151905061029781610273565b92915050565b5f819050919050565b6102af8161029d565b81146102b9575f80fd5b50565b5f815190506102ca816102a6565b92915050565b5f819050919050565b6102e2816102d0565b81146102ec575f80fd5b50565b5f815190506102fd816102d9565b92915050565b5f805f805f8060c0878903121561031d5761031c61023f565b5b5f61032a89828a01610289565b965050602061033b89828a01610289565b955050604061034c89828a016102bc565b945050606061035d89828a016102ef565b935050608061036e89828a01610289565b92505060a061037f89828a01610289565b9150509295509295509295565b61039581610262565b82525050565b5f6020820190506103ae5f83018461038c565b92915050565b60805160a051611d656103ea5f395f81816102cb015261105701525f81816101c8015281816103ba015261101b0152611d655ff3fe60806040526004361061007e575f3560e01c80638da5cb5b1161004d5780638da5cb5b1461011b57806394985ddd14610145578063b37217a41461016d578063f2fde38b1461019d57610085565b806350c5f97514610087578063715018a6146100b157806373a96d04146100c757806378aa08ed146100f157610085565b3661008557005b005b348015610092575f80fd5b5061009b6101c5565b6040516100a891906111e6565b60405180910390f35b3480156100bc575f80fd5b506100c5610263565b005b3480156100d2575f80fd5b506100db610276565b6040516100e89190611279565b60405180910390f35b3480156100fc575f80fd5b5061010561029b565b60405161011291906111e6565b60405180910390f35b348015610126575f80fd5b5061012f6102a1565b60405161013c91906112b2565b60405180910390f35b348015610150575f80fd5b5061016b60048036038101906101669190611339565b6102c9565b005b61018760048036038101906101829190611377565b610365565b60405161019491906113b1565b60405180910390f35b3480156101a8575f80fd5b506101c360048036038101906101be91906113f4565b61055a565b005b5f7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b815260040161021f91906112b2565b602060405180830381865afa15801561023a573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061025e9190611433565b905090565b61026b6105de565b6102745f610665565b565b60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60045481565b5f60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610357576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161034e906114b8565b60405180910390fd5b6103618282610728565b5050565b5f816004546103749190611503565b34146103b5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103ac9061158e565b60405180910390fd5b6003547f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff1660e01b815260040161041191906112b2565b602060405180830381865afa15801561042c573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906104509190611433565b1015610491576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104889061161c565b60405180910390fd5b61049f600254600354611018565b90503360065f8381526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508160075f8381526020019081526020015f2081905550803373ffffffffffffffffffffffffffffffffffffffff167f78d45e221ca4450c23810cead7feee81f5703304a5e653222f5cebf4d6d900d58460405161054d91906111e6565b60405180910390a3919050565b6105626105de565b5f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036105d2575f6040517f1e4fbdf70000000000000000000000000000000000000000000000000000000081526004016105c991906112b2565b60405180910390fd5b6105db81610665565b50565b6105e661115b565b73ffffffffffffffffffffffffffffffffffffffff166106046102a1565b73ffffffffffffffffffffffffffffffffffffffff16146106635761062761115b565b6040517f118cdaa700000000000000000000000000000000000000000000000000000000815260040161065a91906112b2565b60405180910390fd5b565b5f60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508160015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b5f60075f8481526020019081526020015f205490505f60065f8581526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505f8267ffffffffffffffff81111561078c5761078b61163a565b5b6040519080825280602002602001820160405280156107ba5781602001602082028036833780820191505090505b5090505f805b84811015610b5a575f6001606488846040516020016107e0929190611667565b604051602081830303815290604052805190602001205f1c61080291906116bb565b61080c91906116eb565b90505f60328211610820576001905061083f565b6050821161083557600290506001935061083e565b60039050600193505b5b5f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772836040518263ffffffff1660e01b815260040161089a91906111e6565b5f60405180830381865afa1580156108b4573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f820116820180604052508101906108dc9190611841565b511161091d576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610914906118d2565b60405180910390fd5b5f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772836040518263ffffffff1660e01b815260040161097891906111e6565b5f60405180830381865afa158015610992573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f820116820180604052508101906109ba9190611841565b51896103e8866109ca91906116eb565b6040516020016109db929190611667565b604051602081830303815290604052805190602001205f1c6109fd91906116bb565b90505f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16637418b70b84846040518363ffffffff1660e01b8152600401610a5c929190611667565b6020604051808303815f875af1158015610a78573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610a9c9190611433565b905080878681518110610ab257610ab16118f0565b5b60200260200101818152505060055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd308a846040518463ffffffff1660e01b8152600401610b1c9392919061191d565b5f604051808303815f87803b158015610b33575f80fd5b505af1158015610b45573d5f803e3d5ffd5b505050505050505080806001019150506107c0565b50600a84148015610b69575080155b15610fc2575f600a866103e9604051602001610b86929190611998565b604051602081830303815290604052805190602001205f1c610ba891906116bb565b90505f600290505f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772836040518263ffffffff1660e01b8152600401610c0a91906111e6565b5f60405180830381865afa158015610c24573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f82011682018060405250810190610c4c9190611841565b90505f815103610d3c576003915060055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772836040518263ffffffff1660e01b8152600401610cb491906111e6565b5f60405180830381865afa158015610cce573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f82011682018060405250810190610cf69190611841565b90505f815111610d3b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d3290611a09565b60405180910390fd5b5b5f8151896103ea604051602001610d54929190611a60565b604051602081830303815290604052805190602001205f1c610d7691906116bb565b90505f60055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16637418b70b85846040518363ffffffff1660e01b8152600401610dd5929190611667565b6020604051808303815f875af1158015610df1573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610e159190611433565b90505f878681518110610e2b57610e2a6118f0565b5b6020026020010151905060055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd8a30846040518463ffffffff1660e01b8152600401610e939392919061191d565b5f604051808303815f87803b158015610eaa575f80fd5b505af1158015610ebc573d5f803e3d5ffd5b5050505060055f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd308b856040518463ffffffff1660e01b8152600401610f1e9392919061191d565b5f604051808303815f87803b158015610f35575f80fd5b505af1158015610f47573d5f803e3d5ffd5b505050508873ffffffffffffffffffffffffffffffffffffffff167f44415a749c26a3958dd726e62425f42081cce4649781be4553ea9d6ca61153088383604051610f93929190611667565b60405180910390a281888781518110610faf57610fae6118f0565b5b6020026020010181815250505050505050505b8273ffffffffffffffffffffffffffffffffffffffff167fae70af528670c95920a68ff91ec77ac7eb73759dffa95a00ee2f50fcc23ac93d836040516110089190611b3e565b60405180910390a2505050505050565b5f7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff16634000aea07f000000000000000000000000000000000000000000000000000000000000000084865f60405160200161108a929190611b5e565b6040516020818303038152906040526040518463ffffffff1660e01b81526004016110b793929190611be5565b6020604051808303815f875af11580156110d3573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906110f79190611c56565b505f611115845f305f808981526020019081526020015f2054611162565b905060015f808681526020019081526020015f205461113491906116eb565b5f808681526020019081526020015f2081905550611152848261119c565b91505092915050565b5f33905090565b5f8484848460405160200161117a9493929190611c81565b604051602081830303815290604052805190602001205f1c9050949350505050565b5f82826040516020016111b0929190611d04565b60405160208183030381529060405280519060200120905092915050565b5f819050919050565b6111e0816111ce565b82525050565b5f6020820190506111f95f8301846111d7565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f819050919050565b5f61124161123c611237846111ff565b61121e565b6111ff565b9050919050565b5f61125282611227565b9050919050565b5f61126382611248565b9050919050565b61127381611259565b82525050565b5f60208201905061128c5f83018461126a565b92915050565b5f61129c826111ff565b9050919050565b6112ac81611292565b82525050565b5f6020820190506112c55f8301846112a3565b92915050565b5f604051905090565b5f80fd5b5f80fd5b5f819050919050565b6112ee816112dc565b81146112f8575f80fd5b50565b5f81359050611309816112e5565b92915050565b611318816111ce565b8114611322575f80fd5b50565b5f813590506113338161130f565b92915050565b5f806040838503121561134f5761134e6112d4565b5b5f61135c858286016112fb565b925050602061136d85828601611325565b9150509250929050565b5f6020828403121561138c5761138b6112d4565b5b5f61139984828501611325565b91505092915050565b6113ab816112dc565b82525050565b5f6020820190506113c45f8301846113a2565b92915050565b6113d381611292565b81146113dd575f80fd5b50565b5f813590506113ee816113ca565b92915050565b5f60208284031215611409576114086112d4565b5b5f611416848285016113e0565b91505092915050565b5f8151905061142d8161130f565b92915050565b5f60208284031215611448576114476112d4565b5b5f6114558482850161141f565b91505092915050565b5f82825260208201905092915050565b7f4f6e6c7920565246436f6f7264696e61746f722063616e2066756c66696c6c005f82015250565b5f6114a2601f8361145e565b91506114ad8261146e565b602082019050919050565b5f6020820190508181035f8301526114cf81611496565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61150d826111ce565b9150611518836111ce565b9250828202611526816111ce565b9150828204841483151761153d5761153c6114d6565b5b5092915050565b7f4e6f7420656e6f7567682065746865722073656e7400000000000000000000005f82015250565b5f61157860158361145e565b915061158382611544565b602082019050919050565b5f6020820190508181035f8301526115a58161156c565b9050919050565b7f4e6f7420656e6f756768204c494e4b202d2066696c6c20636f6e7472616374205f8201527f77697468204c494e4b0000000000000000000000000000000000000000000000602082015250565b5f61160660298361145e565b9150611611826115ac565b604082019050919050565b5f6020820190508181035f830152611633816115fa565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b5f60408201905061167a5f8301856111d7565b61168760208301846111d7565b9392505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601260045260245ffd5b5f6116c5826111ce565b91506116d0836111ce565b9250826116e0576116df61168e565b5b828206905092915050565b5f6116f5826111ce565b9150611700836111ce565b9250828201905080821115611718576117176114d6565b5b92915050565b5f80fd5b5f601f19601f8301169050919050565b61173b82611722565b810181811067ffffffffffffffff8211171561175a5761175961163a565b5b80604052505050565b5f61176c6112cb565b90506117788282611732565b919050565b5f67ffffffffffffffff8211156117975761179661163a565b5b602082029050602081019050919050565b5f80fd5b5f6117be6117b98461177d565b611763565b905080838252602082019050602084028301858111156117e1576117e06117a8565b5b835b8181101561180a57806117f6888261141f565b8452602084019350506020810190506117e3565b5050509392505050565b5f82601f8301126118285761182761171e565b5b81516118388482602086016117ac565b91505092915050565b5f60208284031215611856576118556112d4565b5b5f82015167ffffffffffffffff811115611873576118726112d8565b5b61187f84828501611814565b91505092915050565b7f4e6f7420656e6f756768207065747300000000000000000000000000000000005f82015250565b5f6118bc600f8361145e565b91506118c782611888565b602082019050919050565b5f6020820190508181035f8301526118e9816118b0565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b5f6060820190506119305f8301866112a3565b61193d60208301856112a3565b61194a60408301846111d7565b949350505050565b5f819050919050565b5f61ffff82169050919050565b5f61198261197d61197884611952565b61121e565b61195b565b9050919050565b61199281611968565b82525050565b5f6040820190506119ab5f8301856111d7565b6119b86020830184611989565b9392505050565b7f4e6f206c6576656c2033207065747320617661696c61626c65000000000000005f82015250565b5f6119f360198361145e565b91506119fe826119bf565b602082019050919050565b5f6020820190508181035f830152611a20816119e7565b9050919050565b5f819050919050565b5f611a4a611a45611a4084611a27565b61121e565b61195b565b9050919050565b611a5a81611a30565b82525050565b5f604082019050611a735f8301856111d7565b611a806020830184611a51565b9392505050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b611ab9816111ce565b82525050565b5f611aca8383611ab0565b60208301905092915050565b5f602082019050919050565b5f611aec82611a87565b611af68185611a91565b9350611b0183611aa1565b805f5b83811015611b31578151611b188882611abf565b9750611b2383611ad6565b925050600181019050611b04565b5085935050505092915050565b5f6020820190508181035f830152611b568184611ae2565b905092915050565b5f604082019050611b715f8301856113a2565b611b7e60208301846111d7565b9392505050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f611bb782611b85565b611bc18185611b8f565b9350611bd1818560208601611b9f565b611bda81611722565b840191505092915050565b5f606082019050611bf85f8301866112a3565b611c0560208301856111d7565b8181036040830152611c178184611bad565b9050949350505050565b5f8115159050919050565b611c3581611c21565b8114611c3f575f80fd5b50565b5f81519050611c5081611c2c565b92915050565b5f60208284031215611c6b57611c6a6112d4565b5b5f611c7884828501611c42565b91505092915050565b5f608082019050611c945f8301876113a2565b611ca160208301866111d7565b611cae60408301856112a3565b611cbb60608301846111d7565b95945050505050565b5f819050919050565b611cde611cd9826112dc565b611cc4565b82525050565b5f819050919050565b611cfe611cf9826111ce565b611ce4565b82525050565b5f611d0f8285611ccd565b602082019150611d1f8284611ced565b602082019150819050939250505056fea2646970667358221220a21de10d0d1a47b1787d39b3b0d50937bff089edd738837d02589f59b28e751164736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_GETLINKBALANCE = "getLinkBalance";

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
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOTTERYREQUESTED_EVENT = new Event("LotteryRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));

    public static final Event P_EVENT = new Event("p",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(false) {}));

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
//            typedResponse.tokenIds = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static LotteryFulfilledEventResponse getLotteryFulfilledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOTTERYFULFILLED_EVENT, log);
        LotteryFulfilledEventResponse typedResponse = new LotteryFulfilledEventResponse();
        typedResponse.log = log;
        typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.tokenIds = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getValue();
        typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

//    public Flowable<LotteryFulfilledEventResponse> lotteryFulfilledEventFlowable(EthFilter filter) {
//        return web3j.ethLogFlowable(filter).map(log -> getLotteryFulfilledEventFromLog(log));
//    }

//    public Flowable<LotteryFulfilledEventResponse> lotteryFulfilledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
//        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
//        filter.addSingleTopic(EventEncoder.encode(LOTTERYFULFILLED_EVENT));
//        return lotteryFulfilledEventFlowable(filter);
//    }

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

    public static PEventResponse getPEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(P_EVENT, log);
        PEventResponse typedResponse = new PEventResponse();
        typedResponse.log = log;
        typedResponse.t = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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

    public RemoteFunctionCall<BigInteger> getLinkBalance() {
        final Function function = new Function(FUNC_GETLINKBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

        public BigInteger endTime;
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

    public static class PEventResponse extends BaseEventResponse {
        public BigInteger t;
    }
}
