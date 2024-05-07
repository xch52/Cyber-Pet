package com.uobfintech.nftpictures.repository;

import jakarta.annotation.PreDestroy;
import lombok.Data;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;


@Component
public class MongoDAO {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDAO(@Value("${spring.data.mongodb.uri}") String connectionString,
                    @Value("${spring.data.mongodb.database}") String databaseName) {
        // mongoClient = MongoClients.create(connectionString);
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(databaseName);
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    @Bean
    public String httpPrefix4Ipfs() {
        return "https://ipfs.io/ipfs/";
    }

    @PreDestroy
    public void close() {
        mongoClient.close();
    }



}

