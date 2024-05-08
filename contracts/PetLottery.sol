// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

// Importing required interfaces and base classes from Chainlink and the custom PetNFT contract
import {IVRFCoordinatorV2Plus} from "@chainlink/contracts/src/v0.8/vrf/dev/interfaces/IVRFCoordinatorV2Plus.sol";
import {VRFConsumerBaseV2Plus} from "@chainlink/contracts/src/v0.8/vrf/dev/VRFConsumerBaseV2Plus.sol";
import {VRFV2PlusClient} from "@chainlink/contracts/src/v0.8/vrf/dev/libraries/VRFV2PlusClient.sol";
import "./PetNFT.sol";

// Define the main contract which inherits from Chainlink's VRFConsumerBaseV2Plus for RNG
contract PetLotteryV2Plus is VRFConsumerBaseV2Plus {
    IVRFCoordinatorV2Plus COORDINATOR;

    // Variables to store subscription and Chainlink VRF related information
    uint256 s_subscriptionId;
    bytes32 s_keyHash = 0x787d74caea10b2b357790d5b5247c2f63d1d91572a9846f780606e4d953677ae;
    uint32 s_callbackGasLimit = 100000;
    uint16 s_requestConfirmations = 3;
    address VRFCoordinator = 0x9DdfaCa8183c41ad55329BdeeD9F6A8d53168B1B;

    // Variables to handle lottery mechanics
    uint256 public lotteryFee = 10000 wei;
    PetNFT public petNFTContract;
    mapping(uint256 => address) private requestToSender;
    mapping(uint256 => uint256) private requestToAmount;

    // Events for logging activities on the blockchain
    event LotteryRequested(address indexed requester, uint256 requestId, uint256 amount);
    event LotteryFulfilled(address indexed requester, uint256[] tokenIds, uint timestamp);
    event HighLevelPetEnsured(address indexed requester, uint256 highLevelTokenId, uint256 replacedTokenId);
    event NFTReceived(address operator, address from, uint256 tokenId, bytes data);

    // Constructor to initialize the contract with Chainlink VRF settings and the PetNFT contract address
    constructor(
        uint256 subscriptionId,
        address petNFTContractAddress
    )
        VRFConsumerBaseV2Plus(VRFCoordinator) {
        COORDINATOR = IVRFCoordinatorV2Plus(VRFCoordinator);
        s_subscriptionId = subscriptionId;
        petNFTContract = PetNFT(petNFTContractAddress);
    }

    // ERC721 compliance for receiving NFTs
    function onERC721Received(
        address operator,
        address from,
        uint256 tokenId,
        bytes calldata data
    ) public returns (bytes4) {
        emit NFTReceived(operator, from, tokenId, data);
        return this.onERC721Received.selector;
    }
    
    // Function to request random numbers from Chainlink VRF
    function requestRandomWords(uint256 amount) public payable returns (uint256 requestId) {
        require(msg.value == lotteryFee * amount, "sent ether does not match the cost");
        bool payWithNativeToken = false; 
        requestId = COORDINATOR.requestRandomWords(
            VRFV2PlusClient.RandomWordsRequest({
                keyHash: s_keyHash,
                subId: s_subscriptionId,
                requestConfirmations: s_requestConfirmations,
                callbackGasLimit: s_callbackGasLimit,
                numWords: uint32(amount),
                extraArgs: VRFV2PlusClient._argsToBytes(
                    VRFV2PlusClient.ExtraArgsV1({nativePayment: payWithNativeToken})
                )
            })
        );

        requestToSender[requestId] = msg.sender;
        requestToAmount[requestId] = amount;
        emit LotteryRequested(msg.sender, requestId, amount);
        return requestId;
    }

    // Internal function overridden to handle the randomness response
    function fulfillRandomWords(uint256 requestId, uint256[] memory randomWords) internal override {
        uint256 amount = requestToAmount[requestId];
        address player = requestToSender[requestId];
        uint256[] memory tokenIds = new uint256[](amount);
        uint256[] memory levels = new uint256[](amount);
        bool hasHighLevelPet = false;

        // Determine all pet levels before any assignments
        for (uint256 i = 0; i < amount; i++) {
            uint256 randomNumber = (randomWords[i] % 100) + 1;
            if (randomNumber <= 50) {
                levels[i] = 1;
            } else if (randomNumber <= 80) {
                levels[i] = 2;
                hasHighLevelPet = true;
            } else {
                levels[i] = 3;
                hasHighLevelPet = true;
            }
        }

        // Swap the pet at the selected position with a high-level pet
        if (amount == 5 && !hasHighLevelPet) {
            uint256 swapIndex = uint256(keccak256(abi.encode(randomWords, randomWords[0]))) % 10;
            levels[swapIndex] = 2; // Start with level 2, check availability below
            hasHighLevelPet = true;
        }

        // Assign pets based on the determined levels
        for (uint256 i = 0; i < amount; i++) {
            require(petNFTContract.getPetsByLevel(levels[i]).length > 0, "Not enough pets");
            uint256 petIndex = randomWords[i] % petNFTContract.getPetsByLevel(levels[i]).length;
            uint256 tokenId = petNFTContract.removePetFromLevel(levels[i], petIndex);
            tokenIds[i] = tokenId;
            petNFTContract.transferFrom(address(this), player, tokenId);
        }

        emit LotteryFulfilled(player, tokenIds, block.timestamp);
    }

    // Fallback and receive functions to handle direct Ether transactions
    receive() external payable {}
    fallback() external payable {}
}
