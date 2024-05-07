package com.uobfintech.nftpictures.entity;

import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    public BigInteger tokenId;

    public String seller;

    public ZonedDateTime startTime;

    public ZonedDateTime endTime;

    public BigInteger timestamp;

    public BigInteger highestBid;

    public String highestBidder;

    public BigInteger reservePrice;
}
