package com.uobfintech.nftpictures.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Pet {
    @Id
    private Long id;

    private String name;

    private String imageUrl;

    private Map<String, String> attributes;

    // private String description;

    private List<History> history;

}
