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
    event AuctionAutoEnded(uint256 indexed tokenId, address winner, uint256 amount);
    event AuctionUpdated(uint256 indexed tokenId, uint256 newReservePrice, uint256 newEndTime);
    event AuctionCancelled(uint256 indexed tokenId);

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
    mapping(address => uint256) public failedRefunds;

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
            (bool success, ) = previousBidder.call{value: previousBid}("");
            if (!success) {
                // if refund failed, user can refund manually
                failedRefunds[previousBidder] += previousBid;
            }
        }

        emit BidPlaced(tokenId, msg.sender, msg.value);
    }

    // refund manually
    function withdrawFailedRefund() external {
        uint256 amount = failedRefunds[msg.sender];
        require(amount > 0, "No funds to withdraw");

        failedRefunds[msg.sender] = 0;
        (bool success, ) = msg.sender.call{value: amount}("");
        require(success, "Withdrawal failed");
    }


    // Function to end an auction.
    function endAuction(uint256 tokenId) public nonReentrant {
        Auction storage auction = auctions[tokenId];
        require(auction.active, "Auction is not active");
        require(block.timestamp > auction.endTime, "Auction has not ended yet");
        // require(msg.sender == auction.seller || msg.sender == auction.highestBidder, "Only seller or highest bidder can end the auction");

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

    function checkAndEndAuctions() external {
        uint256 length = activeAuctions.length();
        for (uint256 i = 0; i < length; i++) {
            uint256 tokenId = activeAuctions.at(i);
            Auction storage auction = auctions[tokenId];
            if (block.timestamp > auction.endTime && auction.active) {
                endAuction(tokenId);
                emit AuctionAutoEnded(tokenId, auction.highestBidder, auction.highestBid);
            }
        }
    }

    // Function to list all active auctions.
    function listActiveAuctions() public view returns (uint256[] memory) {
        return activeAuctions.values(); // Return an array of all active auction token IDs
    }

    // Return all active auctions information and pets information.
    function listActiveAuctionsBrief() public view returns (uint256[] memory, Auction[] memory, PetNFT.PetAttributes[] memory) {
        uint256[] memory tokenIds = activeAuctions.values();
        Auction[] memory auctionsBrief = new Auction[](tokenIds.length);
        PetNFT.PetAttributes[] memory petsBrief = new PetNFT.PetAttributes[](tokenIds.length);
        for (uint i = 0; i < tokenIds.length; i++) {
            auctionsBrief[i] = auctions[tokenIds[i]];
            petsBrief[i] = petNFT.getPetAttributes(tokenIds[i]);
        }
        return (tokenIds, auctionsBrief, petsBrief);
    }

    function getAllAuctionsDetails() public view returns (Auction[] memory) {
        Auction[] memory auctionsDetails = new Auction[](activeAuctions.length());
        for (uint i = 0; i < activeAuctions.length(); i++) {
            uint256 tokenId = activeAuctions.at(i);
            auctionsDetails[i] = auctions[tokenId];
        }
        return auctionsDetails;
    }

    function getAuctionAndPetDetails(uint256 tokenId) public view returns (Auction memory, PetNFT.PetAttributes memory) {
        require(auctions[tokenId].active, "Auction is not active");
        return (auctions[tokenId], petNFT.getPetAttributes(tokenId));
    }

    // Provide a function to allow the auction creator to cancel the auction if there are no bids yet.
    function cancelAuction(uint256 tokenId) external {
        Auction storage auction = auctions[tokenId];
        require(msg.sender == auction.seller, "Only the seller can cancel the auction");
        require(auction.highestBid == 0, "Cannot cancel after bids are placed");
        petNFT.transferFrom(address(this), auction.seller, tokenId);
        auction.active = false;
        activeAuctions.remove(tokenId);
        emit AuctionCancelled(tokenId);
    }
    
    // Allow sellers to update certain parameters of their auction
    function updateAuction(uint256 tokenId, uint256 newReservePrice, uint256 newAuctionDuration) external {
        Auction storage auction = auctions[tokenId];
        require(msg.sender == auction.seller, "Only the seller can update the auction");
        require(auction.highestBid == 0, "Cannot update after bids are placed");

        auction.reservePrice = newReservePrice;
        auction.endTime = block.timestamp + newAuctionDuration;
        emit AuctionUpdated(tokenId, newReservePrice, auction.endTime);
    }

}
