// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PetNFT.sol"; 
import "@openzeppelin/contracts/security/ReentrancyGuard.sol"; 
import "@openzeppelin/contracts/utils/structs/EnumerableSet.sol";

contract PetLottery is ReentrancyGuard {

    address payable public ownerself;
    uint256 public lotteryFee = 0.002 ether;
    PetNFT public petNFT;
    uint256 requestId;

    mapping(uint256 => address) private requestToSender;
    mapping(uint256 => uint256) private requestToAmount;

    event LotteryRequested(address indexed requester, uint256 requestId, uint256 amount);
    event LotteryFulfilled(address indexed requester, uint256 requestId, uint256[] tokenIds, uint timestamp);
    event HighLevelPetEnsured(address indexed requester, uint256 highLevelTokenId, uint256 replacedTokenId);
    event NFTReceived(address operator, address from, uint256 tokenId, bytes data);

    constructor (address petNFTContractAddress) {
        petNFT = PetNFT(petNFTContractAddress);
        ownerself = payable(msg.sender); 
    }

    function onERC721Received(
        address operator,
        address from,
        uint256 tokenId,
        bytes calldata data
    ) public returns (bytes4) {
        emit NFTReceived(operator, from, tokenId, data);
        return this.onERC721Received.selector;
    }
    function requestRandomWords(uint256 amount) public payable returns (uint256[] memory, PetNFT.PetAttributes[] memory) {
        require(msg.value == lotteryFee * amount, "Incorrect lottery fee.");
        ownerself.transfer(msg.value);

        requestToSender[++requestId] = msg.sender;
        requestToAmount[requestId] = amount;
        emit LotteryRequested(msg.sender, requestId, amount);
        uint256[] memory randomWords = new uint256[](amount);
        for(uint256 i = 0; i < amount; ++i) {
            randomWords[i] = uint256(blockhash(block.number - i - 1));
        }
        return fulfillRandomWords(randomWords);
    }

    function fulfillRandomWords(uint256[] memory randomWords) internal returns (uint256[] memory, PetNFT.PetAttributes[] memory){
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
            require(petNFT.getPetsByLevel(levels[i]).length > 0, "Not enough pets");
            uint256 petIndex = randomWords[i] % petNFT.getPetsByLevel(levels[i]).length;
            uint256 tokenId = petNFT.removePetFromLevel(levels[i], petIndex);
            tokenIds[i] = tokenId;
            petNFT.transferFrom(address(this), player, tokenId);
        }

        PetNFT.PetAttributes[] memory petLotteryAttributes = new PetNFT.PetAttributes[](tokenIds.length);
        for (uint i = 0; i < tokenIds.length; i++) {
            petLotteryAttributes[i] = petNFT.getPetAttributes(tokenIds[i]);
        }
        
        emit LotteryFulfilled(player, requestId, tokenIds, block.timestamp);
        return (tokenIds, petLotteryAttributes);
    }

}
