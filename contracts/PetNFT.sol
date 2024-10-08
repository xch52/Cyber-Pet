// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/access/Ownable.sol";


contract PetNFT is ERC721URIStorage, Ownable {
    uint256 public nextTokenId; 
    address private lotteryAddress; 

    struct PetAttributes {
        uint256 level;         // Level of the pet, allowed values are 1, 2, or 3.
        string name;           // Name of the pet.
        string appearance;     // Descriptive word for pet's appearance.
        string character;      // Descriptive word for pet's character traits.
        string description;    // Detailed description of the pet.
        string url;            // URL for pet's image
        string uri;            // URI for pet's metadata.
    }

    // Mapping from token ID to its attributes.
    mapping(uint256 => PetAttributes) public petDetails;
    mapping(uint256 => uint256[]) private petsByLevel;
    event PetMinted(uint256 tokenId, address to, uint256 level, string name, string appearance, string character, string description, string url, string uri);
    
    constructor() ERC721("CryptoPets", "PET") Ownable(msg.sender) {}

    function setLotteryAddress(address _lotteryAddress) external onlyOwner {
        lotteryAddress = _lotteryAddress;
    }
    modifier onlyOwnerOrLottery() {
        require(msg.sender == owner() || msg.sender == lotteryAddress, "Caller is not the owner or the lotterycontract");
        _;
    }
    // Function to mint a new pet NFT.
    function mintPet(
        address to,
        uint256 level,
        string memory name,
        string memory appearance,
        string memory character,
        string memory description,
        string memory url,
        string memory uri
    ) public onlyOwnerOrLottery {
        require(level >= 1 && level <= 3, "Invalid pet level, must be between 1 and 3.");
        uint256 tokenId = nextTokenId++; 
        petDetails[tokenId] = PetAttributes({
            level: level,
            name: name,
            appearance: appearance,
            character: character,
            description: description,
            url: url,
            uri: uri
        });
        _safeMint(to, tokenId); 
        _setTokenURI(tokenId, uri); 
        if(to == lotteryAddress){
            petsByLevel[level].push(tokenId); 
        }
        emit PetMinted(tokenId, to, level, name, appearance, character, description, url, uri);
    }

    // Function to retrieve an array of pet token IDs by their level.
    function getPetsByLevel(uint256 level) public view returns (uint256[] memory) {
        require(level >= 1 && level <= 3, "Invalid pet level, must be between 1 and 3.");
        return (petsByLevel[level]);
    }

    // Function to get attributes of a specific pet by token ID.
    function getPetAttributes(uint256 tokenId) public view returns (PetAttributes memory) {

        return petDetails[tokenId];
    }
    
    // Function to remove a pet from its level and return its token ID.
    function removePetFromLevel(uint256 level, uint256 index) public onlyOwnerOrLottery returns (uint256) {
        uint256[] storage petsAtLevel = petsByLevel[level];
        require(petsAtLevel.length > 0, "No pets available at this level.");
        uint256 tokenId = petsAtLevel[index]; 
        petsAtLevel[index] = petsAtLevel[petsAtLevel.length - 1];
        petsAtLevel.pop();
        return tokenId;
    }

    // Function to remove a pet by tokenId from its level.
    function removePetByTokenId(uint256 tokenId) public onlyOwnerOrLottery {
        PetAttributes storage pet = petDetails[tokenId];
        uint256 level = pet.level;
        uint256[] storage petsAtLevel = petsByLevel[level];
        require(petsAtLevel.length > 0, "No pets available at this level.");

        for (uint256 i = 0; i < petsAtLevel.length; i++) {
            if (petsAtLevel[i] == tokenId) {
                petsAtLevel[i] = petsAtLevel[petsAtLevel.length - 1];
                petsAtLevel.pop();
                break;
            }
        }
    }

    function getMyPets() public view returns (uint256[] memory, PetAttributes[] memory) {
        
        address owner = msg.sender;
        uint256 totalPets = nextTokenId; 
        uint256 ownerCount = balanceOf(owner);
        PetAttributes[] memory petsOwned = new PetAttributes[](ownerCount);
        uint256[] memory tokenIds = new uint256[](ownerCount);
        uint256 counter = 0;

        for (uint256 i = 0; i < totalPets; i++) { 
            if (ownerOf(i) == owner) { 
                petsOwned[counter] = petDetails[i];
                tokenIds[counter] = i;
                counter++;
                if (counter == ownerCount) break;
            }
        }

        return (tokenIds, petsOwned);
    }

}
