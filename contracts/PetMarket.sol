// // SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PetNFT.sol"; // Importing the PetNFT contract for managing NFTs.
import "@openzeppelin/contracts/security/ReentrancyGuard.sol"; // Using OpenZeppelin's ReentrancyGuard for added security against re-entrancy attacks.
import "@openzeppelin/contracts/utils/structs/EnumerableSet.sol"; // Using EnumerableSet to manage collections of unique elements efficiently.

contract PetMarket is ReentrancyGuard {
    using EnumerableSet for EnumerableSet.UintSet;

    PetNFT public petNFT; // Instance of the PetNFT contract to interact with NFTs.
    EnumerableSet.UintSet private activeSales; // Set for tracking active sales.

    struct Sale {
        address seller; // Address of the seller.
        uint256 price; // Price of the NFT.
        bool isActive; // Flag to check if the sale is active.
    }

    mapping(uint256 => Sale) public petSales; // Mapping from token ID to sale information.

    // Events for logging activities on the blockchain.
    event PetListed(uint256 indexed tokenId, address indexed seller, uint256 price);
    event PetSold(uint256 indexed tokenId, address indexed buyer, uint256 price, uint256 timestamp);
    event SaleCancelled(uint256 indexed tokenId);

    // Constructor to initialize the NFT contract address.
    constructor(address _petNFTAddress) {
        petNFT = PetNFT(_petNFTAddress);
    }

    // Lists a pet for sale.
    function listPetForSale(uint256 tokenId, uint256 price) external {
        require(petNFT.ownerOf(tokenId) == msg.sender, "You must own the pet to list it for sale");
        require(petNFT.getApproved(tokenId) == address(this), "Market must be approved to transfer pet");

        petSales[tokenId] = Sale({
            seller: msg.sender,
            price: price,
            isActive: true
        });
        activeSales.add(tokenId);

        emit PetListed(tokenId, msg.sender, price);
    }

    // Cancels an active sale.
    function cancelSale(uint256 tokenId) external {
        require(petSales[tokenId].seller == msg.sender, "Only the seller can cancel the sale");
        require(petSales[tokenId].isActive, "Sale is not active");

        petSales[tokenId].isActive = false;
        activeSales.remove(tokenId);

        emit SaleCancelled(tokenId);
    }

    // Purchases a pet listed for sale.
    function buyPet(uint256 tokenId) external payable nonReentrant {
        require(activeSales.contains(tokenId), "This pet is not for sale");
        Sale storage sale = petSales[tokenId];
        require(msg.value == sale.price, "Insufficient funds sent");

        sale.isActive = false;
        activeSales.remove(tokenId);
        petNFT.safeTransferFrom(sale.seller, msg.sender, tokenId);
        payable(sale.seller).transfer(msg.value);

        emit PetSold(tokenId, msg.sender, sale.price, block.timestamp);
    }

    // Lists all active sales.
    function listActiveSalesInfo() public view returns (uint256[] memory, Sale[] memory, PetNFT.PetAttributes[] memory) {
        uint256[] memory tokenIds = new uint256[](activeSales.length());
        Sale[] memory sales = new Sale[](activeSales.length());
        PetNFT.PetAttributes[] memory attributes = new PetNFT.PetAttributes[](activeSales.length());
        for (uint256 i = 0; i < activeSales.length(); i++) {
            uint256 tokenId = activeSales.at(i);
            tokenIds[i] = tokenId;
            sales[i] = petSales[tokenId];
            attributes[i] = petNFT.getPetAttributes(tokenIds[i]);
        }

        return (tokenIds, sales, attributes);
    }

    // Retrieves the sale details and the pet attributes for a specific token ID.
    function getSaleDetails(uint256 tokenId) public view returns (Sale memory sale, PetNFT.PetAttributes memory attributes) {
        require(petSales[tokenId].isActive, "This pet is not currently for sale.");
        sale = petSales[tokenId];
        attributes = petNFT.getPetAttributes(tokenId);
        return (sale, attributes);
    }
}
