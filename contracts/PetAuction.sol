// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./PetNFT.sol"; // Import the PetNFT contract.
import "@openzeppelin/contracts/security/ReentrancyGuard.sol"; // Import ReentrancyGuard for preventing re-entrancy attacks.
import "@openzeppelin/contracts/utils/structs/EnumerableSet.sol"; // Import EnumerableSet for managing collections of unique elements.

// Auction contract for NFTs, providing functionalities for listing, bidding, and concluding auctions.
contract PetAuction is ReentrancyGuard {
    using EnumerableSet for EnumerableSet.UintSet; // Enable EnumerableSet methods for uint sets.
    EnumerableSet.UintSet private activeAuctions; // Set of active auction token IDs.

    PetNFT public petNFT; // Instance of the PetNFT contract.

    // Struct to store details about an auction.
    struct Auction {
        address seller; // Address of the pet's seller.
        uint256 tokenId; // Token ID of the pet being auctioned.
        uint256 startTime; // Auction start time in UNIX timestamp.
        uint256 endTime; // Auction end time in UNIX timestamp.
        uint256 highestBid; // Current highest bid amount.
        address highestBidder; // Address of the current highest bidder.
        uint256 reservePrice; // Minimum price at which the pet can be sold.
        bool active; // Flag to check if the auction is active.
    }

    mapping(uint256 => Auction) public auctions; // Mapping from token ID to Auction struct.

    // Events that emit on actions taken within the contract.
    event AuctionCreated(uint256 indexed tokenId, uint256 reservePrice, uint256 startTime, uint256 endTime);
    event BidPlaced(uint256 indexed tokenId, address bidder, uint256 amount);
    event AuctionEnded(uint256 indexed tokenId, address winner, uint256 amount);

    // Constructor to set the PetNFT contract address.
    constructor(address _petNFTAddress) {
        petNFT = PetNFT(_petNFTAddress);
    }

    // Function to create an auction.
    function createAuction(uint256 tokenId, uint256 reservePrice, uint256 auctionDuration) external {
        require(petNFT.ownerOf(tokenId) == msg.sender, "Only the owner can create an auction");
        require(petNFT.getApproved(tokenId) == address(this), "Contract needs to be approved to transfer NFT");
        require(reservePrice > 0, "Reserve price must be more than 0");

        uint256 startTime = block.timestamp;
        uint256 endTime = startTime + auctionDuration;

        auctions[tokenId] = Auction({
            seller: msg.sender,
            tokenId: tokenId,
            startTime: startTime,
            endTime: endTime,
            highestBid: 0,
            highestBidder: address(0),
            reservePrice: reservePrice,
            active: true
        });
        activeAuctions.add(tokenId);
        petNFT.transferFrom(msg.sender, address(this), tokenId); // Transfer the NFT to the contract for escrow.
        emit AuctionCreated(tokenId, reservePrice, startTime, endTime);
    }

    // Function to place a bid on an auction.
    function bid(uint256 tokenId) external payable nonReentrant {
        Auction storage auction = auctions[tokenId];
        require(auction.active, "Auction is not active");
        require(block.timestamp >= auction.startTime && block.timestamp <= auction.endTime, "Auction is not in active period");
        require(msg.value >= auction.reservePrice, "Bid must be at least the reserve price");
        require(msg.value > auction.highestBid, "There is already a higher or equal bid");

        address previousBidder = auction.highestBidder;
        uint256 previousBid = auction.highestBid;

        auction.highestBid = msg.value;
        auction.highestBidder = msg.sender;

        if (previousBidder != address(0)) {
            // Refund the previous highest bidder.
            (bool success, ) = previousBidder.call{value: previousBid}("");
            require(success, "Refund failed");
        }

        emit BidPlaced(tokenId, msg.sender, msg.value);
    }

    // Function to end an auction.
    function endAuction(uint256 tokenId) external nonReentrant {
        Auction storage auction = auctions[tokenId];
        require(auction.active, "Auction is not active");
        require(block.timestamp > auction.endTime, "Auction has not ended yet");
        require(msg.sender == auction.seller || msg.sender == auction.highestBidder, "Only seller or highest bidder can end the auction");

        auction.active = false;
        if (auction.highestBid >= auction.reservePrice) {
            // Transfer the NFT to the highest bidder and pay out the seller.
            petNFT.transferFrom(address(this), auction.highestBidder, tokenId); // Transfer the NFT to the highest bidder and pay out the seller.
            payable(auction.seller).transfer(auction.highestBid);
        } else {
            // No valid bids, transfer NFT back to the seller
            petNFT.transferFrom(address(this), auction.seller, tokenId);
        }
        activeAuctions.remove(tokenId); // Remove the auction from the active list
        emit AuctionEnded(tokenId, auction.highestBidder, auction.highestBid); // Emit an event indicating the auction has ended
    }

    // Function to list all active auctions.
    function listActiveAuctions() public view returns (uint256[] memory) {
        return activeAuctions.values(); // Return an array of all active auction token IDs
    }
}
