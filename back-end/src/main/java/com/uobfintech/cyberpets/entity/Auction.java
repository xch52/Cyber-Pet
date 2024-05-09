package com.uobfintech.cyberpets.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    public Integer tokenId;

    public String seller;

    public ZonedDateTime startTime;

    public ZonedDateTime endTime;

    public BigInteger timestamp;

    public Double highestBid;

    public String highestBidder;

    public Double reservePrice;
}
