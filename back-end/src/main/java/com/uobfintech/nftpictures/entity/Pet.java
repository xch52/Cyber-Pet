package com.uobfintech.nftpictures.entity;

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

    private List<String> attributes;

    // private String description;

    private List<History> history;

    private Double price;

    private String states;

}
