package com.uobfintech.nftpictures.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.uobfintech.nftpictures.entity.History;
import com.uobfintech.nftpictures.entity.Pet;
import com.uobfintech.nftpictures.repository.MongoDAO;
import com.uobfintech.nftpictures.service.MetadataService;

@Service
@Slf4j
public class MetadataServiceImpl implements MetadataService {
//    @Autowired
//    private PetRepository petRepository;

    @Autowired
    private MongoDAO mongoDAO;

    public Pet doc2Pet (Document doc) {
        Long id = doc.getLong("id");
        String name = doc.getString("name");
        String imageUrl = doc.getString("url");

        Document attributesDoc = (Document) doc.get("attributes");
        Map<String, String> attributes = new HashMap<>();
        for (Map.Entry<String, Object> entry : attributesDoc.entrySet()) {
            attributes.put(entry.getKey(), entry.getValue().toString());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Document> historyDocs = (List<Document>) doc.get("history");

        List<History> historyList = new ArrayList<>();
        for (Document historyDoc : historyDocs) {
            Integer transactionId = historyDoc.getInteger("transactionId");
            BigDecimal price = BigDecimal.valueOf(historyDoc.getDouble("price"));

            String dateStr = historyDoc.getString("date");
            try {
                LocalDate date = LocalDate.parse(dateStr, formatter);
                System.out.println(date);
                historyList.add(new History(transactionId, date, price));
            } catch (DateTimeParseException e) {
                System.err.println("Failed to parse the date: " + e.getMessage());
            }
        }
        return Pet.builder()
                .id(id)
                .name(name)
                .imageUrl(imageUrl)
                .attributes(attributes)
                .history(historyList)
                .build();
    }

    public Pet findPetById(Long id) {
        MongoCollection<Document> collection = mongoDAO.getCollection("user");
        // 使用id作为查询参数
        Document doc = collection.find(Filters.eq("id", id)).first();
        log.debug(doc.toJson());
        if (doc != null) {
            return doc2Pet(doc);
        }
        log.debug("No document found with ID: " + id);
        return null;
    }

//    public Pet findById(Long id) {
//        return petRepository.findPetByIdIsNotNull(id);
//    }




    @Override
    public List<Pet> findAllPets() {
        // 连接到MongoDB服务器
        MongoCollection<Document> collection = mongoDAO.getCollection("user");

        // 检索所有文档并存储到列表中
        List<Pet> pets = new ArrayList<>();
        FindIterable<Document> documents = collection.find();
        for (Document doc : documents) {
            if (doc != null) {
                pets.add(doc2Pet(doc));
            }
        }

        return pets;
    }
}
