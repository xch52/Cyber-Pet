package com.uobfintech.cyberpets.entity;

import java.util.Arrays;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;

public class EventDefinition {

    // event LotteryRequested(address indexed requester, bytes32 indexed requestId, uint256 amount);
    // event LotteryFulfilled (address indexed requester, uint256[] tokenIds); 11
    // event PetListed(uint256 indexed tokenId, address indexed seller, uint256 price);
    // event PetSold(uint256 indexed tokenId, address indexed buyer, uint256 price); 11
    // event SaleCancelled(uint256 indexed tokenId);
    // event AuctionCreated(uint256 indexed tokenId, uint256 reservePrice, uint256 startTime, uint256 endTime);
    // event BidPlaced(uint256 indexed tokenId, address bidder, uint256 amount);
    // event AuctionEnded(uint256 indexed tokenId, address winner, uint256 amount); 11


    public static final Event LOTTERY_REQUESTED_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Address>(true) {},
                    new TypeReference<Address>(true) {},
                    new TypeReference<Uint256>(false) {}
            )
    );

    public static final Event LOTTERY_FULFILEDD_EVENT = new Event("LotteryFulfilled",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Address>(true) {},
                    new TypeReference<DynamicArray<Uint256>>(false) {}
            )
    );
    public static final Event PET_LISTED_EVENT = new Event("PetListed",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Address>(true) {},
                    new TypeReference<Address>(true) {},
                    new TypeReference<Uint256>(false) {}
            )
    );
    public static final Event PET_SOLD_EVENT = new Event("Approval",
            Arrays.asList(
                    new TypeReference<Uint256>(true) {},
                    new TypeReference<Address>(true) {},
                    new TypeReference<Uint256>(false) {}
            )
    );
    public static final Event SALE_CANCELLED_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Address>(true) {},
                    new TypeReference<Address>(true) {},
                    new TypeReference<Uint256>(false) {}
            )
    );
    public static final Event AUCTION_CREATED_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Address>(true) {},
                    new TypeReference<Address>(true) {},
                    new TypeReference<Uint256>(false) {}
            )
    );
    public static final Event BID_PLACED_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Address>(true) {},
                    new TypeReference<Address>(true) {},
                    new TypeReference<Uint256>(false) {}
            )
    );

    public static final Event AUCTION_ENDED_EVENT = new Event("AuctionEnded",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Uint256>(true) {},
                    new TypeReference<Address>(false) {},
                    new TypeReference<Uint256>(false) {}
            )
    );
}

