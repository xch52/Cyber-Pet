// // SPDX-License-Identifier: MIT
// pragma solidity ^0.8.0;

// import "./PetNFT.sol"; // Importing the PetNFT contract for NFT management.
// import "@openzeppelin/contracts/security/ReentrancyGuard.sol"; // Importing OpenZeppelin's ReentrancyGuard for security against re-entrancy attacks.

// // Contract for managing the marketplace for pet NFTs, including listing, buying, and cancelling sales.
// contract PetMarket is ReentrancyGuard {
//     PetNFT public petNFT; // Instance of the PetNFT contract.

//     // Struct to hold information about a pet sale.
//     struct Sale {
//         address seller; // Address of the pet's seller.
//         uint256 price; // Price of the pet.
//         bool isActive; // Status of the sale (active/inactive).
//     }

//     // Mapping from token ID to Sale struct to track pet sales.
//     mapping(uint256 => Sale) public petSales;

//     // Events for logging actions on the blockchain.
//     event PetListed(uint256 indexed tokenId, address indexed seller, uint256 price);
//     event PetSold(uint256 indexed tokenId, address indexed buyer, uint256 price);
//     event SaleCancelled(uint256 indexed tokenId);

//     // Constructor to set the PetNFT contract address.
//     constructor(address _petNFTAddress) {
//         petNFT = PetNFT(_petNFTAddress);
//     }

//     // Function to list a pet for sale.
//     function listPetForSale(uint256 tokenId, uint256 price) external {
//         require(petNFT.ownerOf(tokenId) == msg.sender, "You must own the pet to list it for sale"); // Ensuring the caller is the owner of the pet.
//         require(petNFT.getApproved(tokenId) == address(this), "Market must be approved to transfer pet"); // Ensuring the market is approved to transfer the pet.

//         petSales[tokenId] = Sale({
//             seller: msg.sender,
//             price: price,
//             isActive: true
//         });

//         emit PetListed(tokenId, msg.sender, price); // Emitting event after listing.
//     }

//     // Function to cancel a pet sale listing.
//     function cancelSale(uint256 tokenId) external {
//         require(petSales[tokenId].seller == msg.sender, "Only the seller can cancel the sale"); // Ensuring only the seller can cancel the sale.
//         require(petSales[tokenId].isActive, "Sale is not active"); // Ensuring the sale is currently active.

//         petSales[tokenId].isActive = false; // Setting the sale as inactive.
//         emit SaleCancelled(tokenId); // Emitting event after cancelling the sale.
//     }

//     // Function for buying a listed pet.
//     function buyPet(uint256 tokenId) external payable nonReentrant {
//         Sale storage sale = petSales[tokenId];
//         require(sale.isActive, "This pet is not for sale"); // Ensuring the pet is actually for sale.
//         require(msg.value == sale.price, "Insufficient funds sent"); // Ensuring sufficient funds are sent.

//         sale.isActive = false; // Setting the sale as inactive after purchase.
//         petNFT.safeTransferFrom(sale.seller, msg.sender, tokenId); // Transferring the pet to the buyer.
//         payable(sale.seller).transfer(msg.value); // Transferring the funds to the seller.
        
//         emit PetSold(tokenId, msg.sender, sale.price); // Emitting event after a successful purchase.
//     }
// }
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
    function listActiveSales() public view returns (uint256[] memory, Sale[] memory) {
        uint256[] memory tokenIds = new uint256[](activeSales.length());
        Sale[] memory sales = new Sale[](activeSales.length());

        for (uint256 i = 0; i < activeSales.length(); i++) {
            uint256 tokenId = activeSales.at(i);
            tokenIds[i] = tokenId;
            sales[i] = petSales[tokenId];
        }

        return (tokenIds, sales);
    }

    // Retrieves the sale details and the pet attributes for a specific token ID.
    function getSaleDetails(uint256 tokenId) public view returns (Sale memory sale, PetNFT.PetAttributes memory attributes) {
        require(petSales[tokenId].isActive, "This pet is not currently for sale.");
        sale = petSales[tokenId];
        attributes = petNFT.getPetAttributes(tokenId);
    }
}
