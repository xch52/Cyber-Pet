package com.uobfintech.cyberpets.entity;

import jnr.a64asm.InstructionGroup;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotteryHistory {
    private String requester;

    private String requestId;

    private ZonedDateTime dateTime;

    private Integer amount;

    private List<Integer> tokenIds;
}
