package com.uobfintech.nftpictures.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
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
        Integer id = doc.getInteger("id");
        String title = doc.getString("title");
        String imageUrl = doc.getString("image");

        Document attributesDoc = (Document) doc.get("attributes");


        List<String> attributes = new ArrayList<>();
        if (doc.containsKey("attribute")) {
            // 确保字段确实是一个列表
            attributes = doc.getList("attribute", String.class);
        } else {
            System.out.println("No interests found or wrong data type.");
        }

        Double price = doc.getDouble("price");
        String states = doc.getString("states");
        String petclass = doc.getString("petclass");
        String description = doc.getString("description");

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        List<Document> historyDocs = (List<Document>) doc.get("history");
//
//        List<History> historyList = new ArrayList<>();
//        for (Document historyDoc : historyDocs) {
//            Integer transactionId = historyDoc.getInteger("transactionId");
//            BigDecimal price = BigDecimal.valueOf(historyDoc.getDouble("price"));
//
//            String dateStr = historyDoc.getString("date");
//            try {
//                LocalDate date = LocalDate.parse(dateStr, formatter);
//                System.out.println(date);
//                historyList.add(new History(transactionId, date, price));
//            } catch (DateTimeParseException e) {
//                System.err.println("Failed to parse the date: " + e.getMessage());
//            }
//        }
        return Pet.builder()
                .id(id)
                .title(title)
                .imageUrl(imageUrl)
                .attributes(attributes)
                .price(price)
                .states(states)
                .petclass(petclass)
                .description(description)
                .build();
    }

    public Pet findPetById(Long id) {
        MongoCollection<Document> collection = mongoDAO.getCollection("pet");
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
        MongoCollection<Document> collection = mongoDAO.getCollection("pet");

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
