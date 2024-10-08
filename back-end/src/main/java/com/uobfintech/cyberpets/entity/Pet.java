package com.uobfintech.cyberpets.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Pet {
    @Id
    private Integer id;

    private String title;

    private String imageUrl;

    private String jsonUri;

    private String petclass;

    private List<String> attributes;

    private List<Double> prebid;

    private String description;

    private List<History> history;

    private List<LotteryHistory> lotteryHistory;

    private List<LotteryDatetime> lotteryDatetime;

    private Double auctionPrice;

    private Double marketPrice;

    private String states;

    private String owner;

}
