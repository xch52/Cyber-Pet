// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@chainlink/contracts/src/v0.8/VRFConsumerBase.sol";
import "./PetNFT.sol";

// A lottery contract for NFT pets that uses Chainlink's VRF for randomness.
contract PetLottery is VRFConsumerBase, Ownable {
    bytes32 internal keyHash; // Identifier for the Chainlink VRF node.
    uint256 internal fee; // Fee in LINK tokens required to request randomness.
    uint256 public lotteryFee = 10 wei; // Fee in ether required to enter the lottery, adjustable as needed.

    PetNFT public petNFTContract; // Reference to the PetNFT contract.
    mapping(bytes32 => address) private requestToSender; // Maps request ID to the lottery participant's address.
    mapping(bytes32 => uint256) private requestToAmount; // Maps request ID to the number of entries.

    // Events to signal lottery actions.
    event LotteryRequested(address indexed requester, bytes32 indexed requestId, uint256 amount);
    event LotteryFulfilled(address indexed requester, uint256[] tokenIds);
    event HighLevelPetEnsured(address indexed requester, uint256 highLevelTokenId, uint256 replacedTokenId);

    // Constructor initializing the Chainlink VRF and the NFT contract.
    constructor(
        address _vrfCoordinator,
        address _linkToken,
        bytes32 _keyHash,
        uint256 _fee,
        address _petNFTContractAddress,
        address initialOwner
    ) VRFConsumerBase(_vrfCoordinator, _linkToken) Ownable(initialOwner) {
        keyHash = _keyHash;
        fee = _fee;
        petNFTContract = PetNFT(_petNFTContractAddress);
    }

    // Public function to enter the lottery, requires payment in ETH.
    function getRandomNumber(uint256 amount) public payable returns (bytes32 requestId) {
        require(msg.value == lotteryFee * amount, "Not enough ether sent");
        require(LINK.balanceOf(address(this)) >= fee, "Not enough LINK - fill contract with LINK");
        requestId = requestRandomness(keyHash, fee);
        requestToSender[requestId] = msg.sender;
        requestToAmount[requestId] = amount;
        emit LotteryRequested(msg.sender, requestId, amount);
        return requestId;
    }

    // Callback function used by VRF to supply randomness.
    function fulfillRandomness(bytes32 requestId, uint256 randomness) internal override {
        uint256 amount = requestToAmount[requestId];
        address player = requestToSender[requestId];
        uint256[] memory tokenIds = new uint256[](amount);
        bool hasHighLevelPet = false;

        for (uint256 i = 0; i < amount; i++) {
            uint256 randomNumber = (uint256(keccak256(abi.encode(randomness, i))) % 100) + 1;
            uint256 level;
            if (randomNumber <= 50) {
                level = 1;
            } else if (randomNumber <= 80) {
                level = 2;
                hasHighLevelPet = true;
            } else {
                level = 3;
                hasHighLevelPet = true;
            }
            require(petNFTContract.getPetsByLevel(level).length > 0, "Not enough pets");
            uint256 petIndex = uint256(keccak256(abi.encode(randomness, i + 1000))) % petNFTContract.getPetsByLevel(level).length;
            uint256 tokenId = petNFTContract.removePetFromLevel(level, petIndex);
            tokenIds[i] = tokenId;
            petNFTContract.transferFrom(address(this), player, tokenId);
        }
        
        // Ensuring at least one high-level pet in case of a 10-pet lottery.
        if (amount == 10 && !hasHighLevelPet) {
            uint256 swapIndex = uint256(keccak256(abi.encode(randomness, 1001))) % 10; // Randomly select a position to replace
            uint256 levelToSwap = 2; // Start trying from level 2
            uint256[] memory petsAtHigherLevel = petNFTContract.getPetsByLevel(levelToSwap);
            if (petsAtHigherLevel.length == 0) {
                levelToSwap = 3; // If no level 2 pets available, try level 3
                petsAtHigherLevel = petNFTContract.getPetsByLevel(levelToSwap);
                require(petsAtHigherLevel.length > 0, "No level 3 pets available");
            }
            uint256 highLevelPetIndex = uint256(keccak256(abi.encode(randomness, 1002))) % petsAtHigherLevel.length;
            uint256 highLevelTokenId = petNFTContract.removePetFromLevel(levelToSwap, highLevelPetIndex);

            // Swap the pet at the selected position with a high-level pet
            uint256 originalTokenId = tokenIds[swapIndex];
            petNFTContract.transferFrom(player, address(this), originalTokenId); // Return the original pet to the contract
            petNFTContract.transferFrom(address(this), player, highLevelTokenId); // Assign the high-level pet to the player
            emit HighLevelPetEnsured(player, highLevelTokenId, originalTokenId); // Emit an event for ensuring a high-level pet
            tokenIds[swapIndex] = highLevelTokenId; // Update the tokenIds array with the high-level pet
        }
        emit LotteryFulfilled(player, tokenIds); // Emit an event once the lottery is successfully fulfilled
    }

    function getLinkBalance() public view returns (uint256) {
        return LINK.balanceOf(address(this));
    }

    // Contract functions to enable receiving and managing Ether
    receive() external payable {}
    fallback() external payable {}
}
