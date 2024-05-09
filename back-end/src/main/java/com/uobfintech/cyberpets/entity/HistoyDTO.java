package com.uobfintech.cyberpets.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoyDTO {
    private String buyerId;

    private String sellerId;

    private String dateTime;

    private Double price;

    private String type;

}
