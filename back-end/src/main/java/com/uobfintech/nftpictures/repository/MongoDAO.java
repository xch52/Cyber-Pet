package com.uobfintech.nftpictures.repository;

import jakarta.annotation.PreDestroy;
import lombok.Data;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoDAO {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDAO(@Value("${spring.data.mongodb.uri}") String connectionString,
                    @Value("${spring.data.mongodb.database}") String databaseName) {
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase(databaseName);
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    @PreDestroy
    public void close() {
        mongoClient.close();
    }
}

