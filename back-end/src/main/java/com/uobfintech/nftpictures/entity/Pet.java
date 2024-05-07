package com.uobfintech.nftpictures.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
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

    private String petclass;

    private List<String> attributes;

    private List<Double> prebid;

    private String description;

    private List<History> history;

    private List<LotteryHistory> lotteryHistory;

    private Double price;

    private String states;

    private String owner;

}
