// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

// Extending ERC721URIStorage for token URI handling and Ownable for ownership management.
contract PetNFT is ERC721URIStorage, Ownable {
    uint256 public nextTokenId; // Tracks the next token ID to be minted.
    struct PetAttributes {
        uint256 level;         // Level of the pet, allowed values are 1, 2, or 3.
        string appearance;     // Descriptive word for pet's appearance.
        string character;      // Descriptive word for pet's character traits.
        string description;    // Detailed description of the pet.
        string uri;            // URI for pet's metadata.
    }

    // Mapping from token ID to its attributes.
    mapping(uint256 => PetAttributes) public petDetails;
    // Mapping from level to array of token IDs, used to store pets by their levels.
    mapping(uint256 => uint256[]) private petsByLevel;

    // Constructor to initialize the NFT collection.
    constructor() ERC721("CryptoPets", "PET") Ownable(msg.sender) {}

    // Function to mint a new pet NFT.
    function mintPet(
        address to,
        uint256 level,
        string memory appearance,
        string memory character,
        string memory description,
        string memory uri
    ) public onlyOwner {
        require(level >= 1 && level <= 3, "Invalid pet level, must be between 1 and 3.");
        uint256 tokenId = nextTokenId++; // Increment the tokenId for each new pet.
        petDetails[tokenId] = PetAttributes({
            level: level,
            appearance: appearance,
            character: character,
            description: description,
            uri: uri
        });
        _safeMint(to, tokenId); // Mint the token.
        _setTokenURI(tokenId, uri); // Set the URI for the token.
        petsByLevel[level].push(tokenId); // Add the token ID to the array for the corresponding level.
    }

    // Function to retrieve an array of pet token IDs by their level.
    function getPetsByLevel(uint256 level) public view returns (uint256[] memory) {
        require(level >= 1 && level <= 3, "Invalid pet level, must be between 1 and 3.");
        return petsByLevel[level];
    }

    // Function to get attributes of a specific pet by token ID.
    function getPetAttributes(uint256 tokenId) public view returns (PetAttributes memory) {
        return petDetails[tokenId];
    }
    
    // Function to remove a pet from its level and return its token ID.
    function removePetFromLevel(uint256 level, uint256 index) public onlyOwner returns (uint256) {
        uint256[] storage petsAtLevel = petsByLevel[level];
        require(petsAtLevel.length > 0, "No pets available at this level.");
        uint256 tokenId = petsAtLevel[index]; // Get the token ID at the specified index.
        petsAtLevel[index] = petsAtLevel[petsAtLevel.length - 1]; // Move the last token in the array to the index position.
        petsAtLevel.pop(); // Remove the last element in the array.
        return tokenId; // Return the removed token ID.
    }

    // Function to remove a pet by tokenId from its level.
    function removePetByTokenId(uint256 tokenId) public onlyOwner {
        PetAttributes storage pet = petDetails[tokenId];
        uint256 level = pet.level;
        uint256[] storage petsAtLevel = petsByLevel[level];
        require(petsAtLevel.length > 0, "No pets available at this level.");

        // Find the index of the tokenId in the array
        for (uint256 i = 0; i < petsAtLevel.length; i++) {
            if (petsAtLevel[i] == tokenId) {
                petsAtLevel[i] = petsAtLevel[petsAtLevel.length - 1]; // Move the last token in the array to the index position.
                petsAtLevel.pop(); // Remove the last element in the array.
                break;
            }
        }
    }

}
