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
import org.web3j.abi.datatypes.DynamicBytes;
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
    public static final String BINARY = "60806040527f787d74caea10b2b357790d5b5247c2f63d1d91572a9846f780606e4d953677ae5f1b600555620186a060065f6101000a81548163ffffffff021916908363ffffffff1602179055506003600660046101000a81548161ffff021916908361ffff160217905550739ddfaca8183c41ad55329bdeed9f6a8d53168b1b6006806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600a6007553480156100cf575f80fd5b5060405161255038038061255083398181016040528101906100f19190610517565b60068054906101000a900473ffffffffffffffffffffffffffffffffffffffff1633805f8073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1603610183576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161017a906105af565b60405180910390fd5b815f806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610205576102048161035c60201b60201c565b5b5050505f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361026d576040517fd92e233d00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b8060025f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505060068054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660035f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550816004819055508060085f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050610635565b3373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036103ca576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103c190610617565b60405180910390fd5b8060015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff165f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167fed8889f560326eb138920d842192f0eb3dd22b4f139c87a2c57538e05bae127860405160405180910390a350565b5f80fd5b5f819050919050565b61049c8161048a565b81146104a6575f80fd5b50565b5f815190506104b781610493565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6104e6826104bd565b9050919050565b6104f6816104dc565b8114610500575f80fd5b50565b5f81519050610511816104ed565b92915050565b5f806040838503121561052d5761052c610486565b5b5f61053a858286016104a9565b925050602061054b85828601610503565b9150509250929050565b5f82825260208201905092915050565b7f43616e6e6f7420736574206f776e657220746f207a65726f00000000000000005f82015250565b5f610599601883610555565b91506105a482610565565b602082019050919050565b5f6020820190508181035f8301526105c68161058d565b9050919050565b7f43616e6e6f74207472616e7366657220746f2073656c660000000000000000005f82015250565b5f610601601783610555565b915061060c826105cd565b602082019050919050565b5f6020820190508181035f83015261062e816105f5565b9050919050565b611f0e806106425f395ff3fe608060405260043610610094575f3560e01c80638da5cb5b116100585780638da5cb5b1461016b5780638ea98117146101955780639eccacf6146101bd578063bc85777c146101e7578063f2fde38b146102175761009b565b8063150b7a021461009d5780631fe543e3146100d957806373a96d041461010157806378aa08ed1461012b57806379ba5097146101555761009b565b3661009b57005b005b3480156100a8575f80fd5b506100c360048036038101906100be91906112b0565b61023f565b6040516100d0919061136e565b60405180910390f35b3480156100e4575f80fd5b506100ff60048036038101906100fa91906114cf565b610292565b005b34801561010c575f80fd5b50610115610354565b6040516101229190611584565b60405180910390f35b348015610136575f80fd5b5061013f610379565b60405161014c91906115ac565b60405180910390f35b348015610160575f80fd5b5061016961037f565b005b348015610176575f80fd5b5061017f61050e565b60405161018c91906115d4565b60405180910390f35b3480156101a0575f80fd5b506101bb60048036038101906101b691906115ed565b610535565b005b3480156101c8575f80fd5b506101d1610712565b6040516101de9190611638565b60405180910390f35b61020160048036038101906101fc9190611651565b610737565b60405161020e91906115ac565b60405180910390f35b348015610222575f80fd5b5061023d600480360381019061023891906115ed565b610958565b005b5f7f1d823cdc8f0514a95b53538df2d2f3deaf98d1c534c6e750daa593173c27f8f086868686866040516102779594939291906116c6565b60405180910390a163150b7a0260e01b905095945050505050565b60025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610346573360025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff166040517f1cf993f400000000000000000000000000000000000000000000000000000000815260040161033d929190611712565b60405180910390fd5b610350828261096c565b5050565b60085f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60075481565b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461040e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161040590611793565b60405180910390fd5b5f805f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050335f806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505f60015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a350565b5f805f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b61053d61050e565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580156105c5575060025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614155b1561063357336105d361050e565b60025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff166040517f061db9c100000000000000000000000000000000000000000000000000000000815260040161062a939291906117b1565b60405180910390fd5b5f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610698576040517fd92e233d00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b8060025f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507fd1a6a14209a385a964d036e404cb5cfb71f4000cdb03c9366292430787261be68160405161070791906115d4565b60405180910390a150565b60025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f816007546107469190611813565b3414610787576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161077e906118c4565b60405180910390fd5b5f60035f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16639b1c385e6040518060c0016040528060055481526020016004548152602001600660049054906101000a900461ffff1661ffff16815260200160065f9054906101000a900463ffffffff1663ffffffff1681526020018663ffffffff16815260200161083c6040518060200160405280871515815250610f60565b8152506040518263ffffffff1660e01b815260040161085b9190611a29565b6020604051808303815f875af1158015610877573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061089b9190611a5d565b91503360095f8481526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600a5f8481526020019081526020015f20819055503373ffffffffffffffffffffffffffffffffffffffff167f47f88e0d63bb9c400c53b58a4d7662a41eb1d83ea13362fda46fdb655559d328838560405161094a929190611a88565b60405180910390a250919050565b610960610ff8565b61096981611087565b50565b5f600a5f8481526020019081526020015f205490505f60095f8581526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505f8267ffffffffffffffff8111156109d0576109cf611397565b5b6040519080825280602002602001820160405280156109fe5781602001602082028036833780820191505090505b5090505f8367ffffffffffffffff811115610a1c57610a1b611397565b5b604051908082528060200260200182016040528015610a4a5781602001602082028036833780820191505090505b5090505f805b85811015610b23575f60016064898481518110610a7057610a6f611aaf565b5b6020026020010151610a829190611b09565b610a8c9190611b39565b905060328111610abc576001848381518110610aab57610aaa611aaf565b5b602002602001018181525050610b15565b60508111610aee576002848381518110610ad957610ad8611aaf565b5b60200260200101818152505060019250610b14565b6003848381518110610b0357610b02611aaf565b5b602002602001018181525050600192505b5b508080600101915050610a50565b50600a85148015610b32575080155b15610bb2575f600a87885f81518110610b4e57610b4d611aaf565b5b6020026020010151604051602001610b67929190611c14565b604051602081830303815290604052805190602001205f1c610b899190611b09565b90506002838281518110610ba057610b9f611aaf565b5b60200260200101818152505060019150505b5f5b85811015610f06575f60085f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772858481518110610c0d57610c0c611aaf565b5b60200260200101516040518263ffffffff1660e01b8152600401610c3191906115ac565b5f60405180830381865afa158015610c4b573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f82011682018060405250810190610c739190611cd7565b5111610cb4576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610cab90611d68565b60405180910390fd5b5f60085f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663abd5c772858481518110610d0557610d04611aaf565b5b60200260200101516040518263ffffffff1660e01b8152600401610d2991906115ac565b5f60405180830381865afa158015610d43573d5f803e3d5ffd5b505050506040513d5f823e3d601f19601f82011682018060405250810190610d6b9190611cd7565b51888381518110610d7f57610d7e611aaf565b5b6020026020010151610d919190611b09565b90505f60085f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16637418b70b868581518110610de457610de3611aaf565b5b6020026020010151846040518363ffffffff1660e01b8152600401610e0a929190611a88565b6020604051808303815f875af1158015610e26573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610e4a9190611a5d565b905080868481518110610e6057610e5f611aaf565b5b60200260200101818152505060085f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3089846040518463ffffffff1660e01b8152600401610eca93929190611d86565b5f604051808303815f87803b158015610ee1575f80fd5b505af1158015610ef3573d5f803e3d5ffd5b5050505050508080600101915050610bb4565b508373ffffffffffffffffffffffffffffffffffffffff167f8da23f89d646b295b0ba64329e26a26be288743ac320a4ba595e5f6077d2a9a58442604051610f4f929190611c14565b60405180910390a250505050505050565b60607f92fd13387c7fe7befbc38d303d6468778fb9731bc4583f17d92989c6fcfdeaaa82604051602401610f949190611def565b604051602081830303815290604052907bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19166020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff83818316178352505050509050919050565b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611085576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161107c90611e52565b60405180910390fd5b565b3373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036110f5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016110ec90611eba565b60405180910390fd5b8060015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff165f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167fed8889f560326eb138920d842192f0eb3dd22b4f139c87a2c57538e05bae127860405160405180910390a350565b5f604051905090565b5f80fd5b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6111eb826111c2565b9050919050565b6111fb816111e1565b8114611205575f80fd5b50565b5f81359050611216816111f2565b92915050565b5f819050919050565b61122e8161121c565b8114611238575f80fd5b50565b5f8135905061124981611225565b92915050565b5f80fd5b5f80fd5b5f80fd5b5f8083601f8401126112705761126f61124f565b5b8235905067ffffffffffffffff81111561128d5761128c611253565b5b6020830191508360018202830111156112a9576112a8611257565b5b9250929050565b5f805f805f608086880312156112c9576112c86111ba565b5b5f6112d688828901611208565b95505060206112e788828901611208565b94505060406112f88882890161123b565b935050606086013567ffffffffffffffff811115611319576113186111be565b5b6113258882890161125b565b92509250509295509295909350565b5f7fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b61136881611334565b82525050565b5f6020820190506113815f83018461135f565b92915050565b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6113cd82611387565b810181811067ffffffffffffffff821117156113ec576113eb611397565b5b80604052505050565b5f6113fe6111b1565b905061140a82826113c4565b919050565b5f67ffffffffffffffff82111561142957611428611397565b5b602082029050602081019050919050565b5f61144c6114478461140f565b6113f5565b9050808382526020820190506020840283018581111561146f5761146e611257565b5b835b818110156114985780611484888261123b565b845260208401935050602081019050611471565b5050509392505050565b5f82601f8301126114b6576114b561124f565b5b81356114c684826020860161143a565b91505092915050565b5f80604083850312156114e5576114e46111ba565b5b5f6114f28582860161123b565b925050602083013567ffffffffffffffff811115611513576115126111be565b5b61151f858286016114a2565b9150509250929050565b5f819050919050565b5f61154c611547611542846111c2565b611529565b6111c2565b9050919050565b5f61155d82611532565b9050919050565b5f61156e82611553565b9050919050565b61157e81611564565b82525050565b5f6020820190506115975f830184611575565b92915050565b6115a68161121c565b82525050565b5f6020820190506115bf5f83018461159d565b92915050565b6115ce816111e1565b82525050565b5f6020820190506115e75f8301846115c5565b92915050565b5f60208284031215611602576116016111ba565b5b5f61160f84828501611208565b91505092915050565b5f61162282611553565b9050919050565b61163281611618565b82525050565b5f60208201905061164b5f830184611629565b92915050565b5f60208284031215611666576116656111ba565b5b5f6116738482850161123b565b91505092915050565b5f82825260208201905092915050565b828183375f83830152505050565b5f6116a5838561167c565b93506116b283858461168c565b6116bb83611387565b840190509392505050565b5f6080820190506116d95f8301886115c5565b6116e660208301876115c5565b6116f3604083018661159d565b818103606083015261170681848661169a565b90509695505050505050565b5f6040820190506117255f8301856115c5565b61173260208301846115c5565b9392505050565b5f82825260208201905092915050565b7f4d7573742062652070726f706f736564206f776e6572000000000000000000005f82015250565b5f61177d601683611739565b915061178882611749565b602082019050919050565b5f6020820190508181035f8301526117aa81611771565b9050919050565b5f6060820190506117c45f8301866115c5565b6117d160208301856115c5565b6117de60408301846115c5565b949350505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61181d8261121c565b91506118288361121c565b92508282026118368161121c565b9150828204841483151761184d5761184c6117e6565b5b5092915050565b7f73656e7420657468657220646f6573206e6f74206d617463682074686520636f5f8201527f7374000000000000000000000000000000000000000000000000000000000000602082015250565b5f6118ae602283611739565b91506118b982611854565b604082019050919050565b5f6020820190508181035f8301526118db816118a2565b9050919050565b5f819050919050565b6118f4816118e2565b82525050565b6119038161121c565b82525050565b5f61ffff82169050919050565b61191f81611909565b82525050565b5f63ffffffff82169050919050565b61193d81611925565b82525050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f61197582611943565b61197f818561194d565b935061198f81856020860161195d565b61199881611387565b840191505092915050565b5f60c083015f8301516119b85f8601826118eb565b5060208301516119cb60208601826118fa565b5060408301516119de6040860182611916565b5060608301516119f16060860182611934565b506080830151611a046080860182611934565b5060a083015184820360a0860152611a1c828261196b565b9150508091505092915050565b5f6020820190508181035f830152611a4181846119a3565b905092915050565b5f81519050611a5781611225565b92915050565b5f60208284031215611a7257611a716111ba565b5b5f611a7f84828501611a49565b91505092915050565b5f604082019050611a9b5f83018561159d565b611aa8602083018461159d565b9392505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601260045260245ffd5b5f611b138261121c565b9150611b1e8361121c565b925082611b2e57611b2d611adc565b5b828206905092915050565b5f611b438261121c565b9150611b4e8361121c565b9250828201905080821115611b6657611b656117e6565b5b92915050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f611ba083836118fa565b60208301905092915050565b5f602082019050919050565b5f611bc282611b6c565b611bcc8185611b76565b9350611bd783611b86565b805f5b83811015611c07578151611bee8882611b95565b9750611bf983611bac565b925050600181019050611bda565b5085935050505092915050565b5f6040820190508181035f830152611c2c8185611bb8565b9050611c3b602083018461159d565b9392505050565b5f611c54611c4f8461140f565b6113f5565b90508083825260208201905060208402830185811115611c7757611c76611257565b5b835b81811015611ca05780611c8c8882611a49565b845260208401935050602081019050611c79565b5050509392505050565b5f82601f830112611cbe57611cbd61124f565b5b8151611cce848260208601611c42565b91505092915050565b5f60208284031215611cec57611ceb6111ba565b5b5f82015167ffffffffffffffff811115611d0957611d086111be565b5b611d1584828501611caa565b91505092915050565b7f4e6f7420656e6f756768207065747300000000000000000000000000000000005f82015250565b5f611d52600f83611739565b9150611d5d82611d1e565b602082019050919050565b5f6020820190508181035f830152611d7f81611d46565b9050919050565b5f606082019050611d995f8301866115c5565b611da660208301856115c5565b611db3604083018461159d565b949350505050565b5f8115159050919050565b611dcf81611dbb565b82525050565b602082015f820151611de95f850182611dc6565b50505050565b5f602082019050611e025f830184611dd5565b92915050565b7f4f6e6c792063616c6c61626c65206279206f776e6572000000000000000000005f82015250565b5f611e3c601683611739565b9150611e4782611e08565b602082019050919050565b5f6020820190508181035f830152611e6981611e30565b9050919050565b7f43616e6e6f74207472616e7366657220746f2073656c660000000000000000005f82015250565b5f611ea4601783611739565b9150611eaf82611e70565b602082019050919050565b5f6020820190508181035f830152611ed181611e98565b905091905056fea2646970667358221220dc9ce063da9c78ecb7e3ce813910f06f6ada8d24ef7e93b009c30196ef5329c364736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ACCEPTOWNERSHIP = "acceptOwnership";

    public static final String FUNC_LOTTERYFEE = "lotteryFee";

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PETNFTCONTRACT = "petNFTContract";

    public static final String FUNC_RAWFULFILLRANDOMWORDS = "rawFulfillRandomWords";

    public static final String FUNC_REQUESTRANDOMWORDS = "requestRandomWords";

    public static final String FUNC_S_VRFCOORDINATOR = "s_vrfCoordinator";

    public static final String FUNC_SETCOORDINATOR = "setCoordinator";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event COORDINATORSET_EVENT = new Event("CoordinatorSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event HIGHLEVELPETENSURED_EVENT = new Event("HighLevelPetEnsured", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOTTERYFULFILLED_EVENT = new Event("LotteryFulfilled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOTTERYREQUESTED_EVENT = new Event("LotteryRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event NFTRECEIVED_EVENT = new Event("NFTReceived", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERREQUESTED_EVENT = new Event("OwnershipTransferRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
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

//    public static List<CoordinatorSetEventResponse> getCoordinatorSetEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(COORDINATORSET_EVENT, transactionReceipt);
//        ArrayList<CoordinatorSetEventResponse> responses = new ArrayList<CoordinatorSetEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            CoordinatorSetEventResponse typedResponse = new CoordinatorSetEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.vrfCoordinator = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static CoordinatorSetEventResponse getCoordinatorSetEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COORDINATORSET_EVENT, log);
        CoordinatorSetEventResponse typedResponse = new CoordinatorSetEventResponse();
        typedResponse.log = log;
        typedResponse.vrfCoordinator = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<CoordinatorSetEventResponse> coordinatorSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCoordinatorSetEventFromLog(log));
    }

    public Flowable<CoordinatorSetEventResponse> coordinatorSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(COORDINATORSET_EVENT));
        return coordinatorSetEventFlowable(filter);
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
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(HIGHLEVELPETENSURED_EVENT, log);
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
//            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static LotteryFulfilledEventResponse getLotteryFulfilledEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOTTERYFULFILLED_EVENT, log);
        LotteryFulfilledEventResponse typedResponse = new LotteryFulfilledEventResponse();
        typedResponse.log = log;
        typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.tokenIds = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getValue();
        typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
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
//            typedResponse.requestId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static LotteryRequestedEventResponse getLotteryRequestedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOTTERYREQUESTED_EVENT, log);
        LotteryRequestedEventResponse typedResponse = new LotteryRequestedEventResponse();
        typedResponse.log = log;
        typedResponse.requester = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.requestId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
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

//    public static List<NFTReceivedEventResponse> getNFTReceivedEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NFTRECEIVED_EVENT, transactionReceipt);
//        ArrayList<NFTReceivedEventResponse> responses = new ArrayList<NFTReceivedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            NFTReceivedEventResponse typedResponse = new NFTReceivedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.operator = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.from = (String) eventValues.getNonIndexedValues().get(1).getValue();
//            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
//            typedResponse.data = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static NFTReceivedEventResponse getNFTReceivedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NFTRECEIVED_EVENT, log);
        NFTReceivedEventResponse typedResponse = new NFTReceivedEventResponse();
        typedResponse.log = log;
        typedResponse.operator = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.from = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.data = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<NFTReceivedEventResponse> nFTReceivedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNFTReceivedEventFromLog(log));
    }

    public Flowable<NFTReceivedEventResponse> nFTReceivedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NFTRECEIVED_EVENT));
        return nFTReceivedEventFlowable(filter);
    }

//    public static List<OwnershipTransferRequestedEventResponse> getOwnershipTransferRequestedEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERREQUESTED_EVENT, transactionReceipt);
//        ArrayList<OwnershipTransferRequestedEventResponse> responses = new ArrayList<OwnershipTransferRequestedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            OwnershipTransferRequestedEventResponse typedResponse = new OwnershipTransferRequestedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static OwnershipTransferRequestedEventResponse getOwnershipTransferRequestedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERREQUESTED_EVENT, log);
        OwnershipTransferRequestedEventResponse typedResponse = new OwnershipTransferRequestedEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferRequestedEventResponse> ownershipTransferRequestedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferRequestedEventFromLog(log));
    }

    public Flowable<OwnershipTransferRequestedEventResponse> ownershipTransferRequestedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERREQUESTED_EVENT));
        return ownershipTransferRequestedEventFlowable(filter);
    }

//    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
//        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
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

    public RemoteFunctionCall<TransactionReceipt> acceptOwnership() {
        final Function function = new Function(
                FUNC_ACCEPTOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> lotteryFee() {
        final Function function = new Function(FUNC_LOTTERYFEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> onERC721Received(String operator, String from, BigInteger tokenId, byte[] data) {
        final Function function = new Function(
                FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new Address(160, operator),
                new Address(160, from),
                new Uint256(tokenId),
                new DynamicBytes(data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<TransactionReceipt> rawFulfillRandomWords(BigInteger requestId, List<BigInteger> randomWords) {
        final Function function = new Function(
                FUNC_RAWFULFILLRANDOMWORDS, 
                Arrays.<Type>asList(new Uint256(requestId),
                new DynamicArray<Uint256>(
                        Uint256.class,
                        org.web3j.abi.Utils.typeMap(randomWords, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestRandomWords(BigInteger amount, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REQUESTRANDOMWORDS, 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<String> s_vrfCoordinator() {
        final Function function = new Function(FUNC_S_VRFCOORDINATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setCoordinator(String _vrfCoordinator) {
        final Function function = new Function(
                FUNC_SETCOORDINATOR, 
                Arrays.<Type>asList(new Address(160, _vrfCoordinator)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String to) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(160, to)),
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

    public static RemoteCall<PetLottery_abi> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger subscriptionId, String petNFTContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(subscriptionId),
                new Address(160, petNFTContractAddress)));
        return deployRemoteCall(PetLottery_abi.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<PetLottery_abi> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger subscriptionId, String petNFTContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(subscriptionId),
                new Address(160, petNFTContractAddress)));
        return deployRemoteCall(PetLottery_abi.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PetLottery_abi> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger subscriptionId, String petNFTContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(subscriptionId),
                new Address(160, petNFTContractAddress)));
        return deployRemoteCall(PetLottery_abi.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PetLottery_abi> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger subscriptionId, String petNFTContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(subscriptionId),
                new Address(160, petNFTContractAddress)));
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

    public static class CoordinatorSetEventResponse extends BaseEventResponse {
        public String vrfCoordinator;
    }

    public static class HighLevelPetEnsuredEventResponse extends BaseEventResponse {
        public String requester;

        public BigInteger highLevelTokenId;

        public BigInteger replacedTokenId;
    }

    public static class LotteryFulfilledEventResponse extends BaseEventResponse {
        public String requester;

        public List<BigInteger> tokenIds;

        public BigInteger timestamp;
    }

    public static class LotteryRequestedEventResponse extends BaseEventResponse {
        public String requester;

        public BigInteger requestId;

        public BigInteger amount;
    }

    public static class NFTReceivedEventResponse extends BaseEventResponse {
        public String operator;

        public String from;

        public BigInteger tokenId;

        public byte[] data;
    }

    public static class OwnershipTransferRequestedEventResponse extends BaseEventResponse {
        public String from;

        public String to;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String from;

        public String to;
    }
}
