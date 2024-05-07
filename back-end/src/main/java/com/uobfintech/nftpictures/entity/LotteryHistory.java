package com.uobfintech.nftpictures.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotteryHistory {
    private String requester;

    private LocalDate date;

    private BigDecimal price;

    private List<BigInteger> tokenIds;
}
