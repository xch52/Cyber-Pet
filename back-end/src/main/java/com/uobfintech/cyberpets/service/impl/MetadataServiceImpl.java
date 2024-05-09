package com.uobfintech.cyberpets.service.impl;

import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.uobfintech.cyberpets.entity.History;
import com.uobfintech.cyberpets.entity.HistoyDTO;
import com.uobfintech.cyberpets.entity.LotteryDatetime;
import com.uobfintech.cyberpets.entity.LotteryHistory;
import com.uobfintech.cyberpets.entity.Pet;
import com.uobfintech.cyberpets.repository.MongoDAO;
import com.uobfintech.cyberpets.service.HistoryService;
import com.uobfintech.cyberpets.service.MetadataService;
import com.uobfintech.cyberpets.entity.HistoyDTO;

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
        String jsonUri = doc.getString("json_uri");

        // Document attributesDoc = (Document) doc.get("attributes");

        List<History> historyList = new ArrayList<>();
        List<String> attributes = new ArrayList<>();
        if (doc.containsKey("attributes")) {
            // 确保字段确实是一个列表
            attributes = doc.getList("attributes", String.class);
        } else {
            System.out.println("No interests found or wrong data type.");
        }

        Double auctionPrice = doc.getDouble("auction_price");
        Double marketPrice = doc.getDouble("market_price");
        String states = doc.getString("states");
        String petclass = doc.getString("petclass");
        String description = doc.getString("description");
        List<Double> prebid = doc.getList("prebid", Double.class);
        String owner = doc.getString("owner");
        List<LotteryDatetime> lotteryDatetimeList = new ArrayList<>();
        if (doc.getList("lottery_hisotory", String.class) != null){
            List<String> lottery_s = doc.getList("lottery_hisotory", String.class);
        }

        System.out.println("lotteryHistory: "+doc.getList("lottery_history", String.class));
        //List<Document> history = (List<Document>) doc.get("history");
        // 假设 'doc' 是你从数据库查询到的 Document 对象

        if (doc.get("history")!=null && doc.getList("lottery_history", String.class) == null){


            List<Map> rawHistoryList = doc.getList("history", Map.class);  // 获取原始数据列表
            for (Map map:rawHistoryList){
                History history1 = new History();
                history1.setBuyerId((String) map.get("buyerId"));
                history1.setDateTime(ZonedDateTime.parse(String.valueOf(map.get("dateTime"))));
                history1.setSellerId((String) map.get("sellerId"));
                history1.setPrice((Double) map.get("price"));
                history1.setType(String.valueOf(map.get("type")));
                historyList.add(history1);
            }


            // List<History> historyList = History.convertToListOfHistory(rawHistoryList);


//            String jsonHistory = doc.get("history").toString();
//
//            Gson gson = new Gson();
//            Type historyListType = new TypeToken<List<History>>(){}.getType();
            // List<HistoyDTO> historyDTOList = gson.fromJson(jsonHistory, historyListType);

//            for (HistoyDTO dto: historyDTOList){
//                historyList.add(new History(dto.getBuyerId(), dto.getSellerId(), ZonedDateTime.parse(dto.getDateTime()), dto.getPrice(), dto.getType()));
//            }
            return Pet.builder()
                    .id(id)
                    .title(title)
                    .imageUrl(imageUrl)
                    .jsonUri(jsonUri)
                    .prebid(prebid)
                    .attributes(attributes)
                    .auctionPrice(auctionPrice)
                    .marketPrice(marketPrice)
                    .states(states)
                    .petclass(petclass)
                    .description(description)
                    .owner(owner)
                    .history(historyList)
                    .build();
        } else if (doc.get("history")!=null && doc.getList("lottery_history", String.class) != null){
            List<String> lottery_s = doc.getList("lottery_history", String.class);
            for (String s : lottery_s){
                LotteryDatetime lotteryDatetime = LotteryDatetime.builder().dateTime(
                        ZonedDateTime.parse(s)
                ).build();
                lotteryDatetimeList.add(lotteryDatetime);
            }

            List<Map> rawHistoryList = doc.getList("history", Map.class);  // 获取原始数据列表
            for (Map map:rawHistoryList){
                History history1 = new History();
                history1.setBuyerId((String) map.get("buyerId"));
                history1.setDateTime(ZonedDateTime.parse(String.valueOf(map.get("dateTime"))));
                history1.setSellerId((String) map.get("sellerId"));
                history1.setPrice((Double) map.get("price"));
                history1.setType(String.valueOf(map.get("type")));
                historyList.add(history1);
            }
            return Pet.builder()
                    .id(id)
                    .title(title)
                    .imageUrl(imageUrl)
                    .jsonUri(jsonUri)
                    .prebid(prebid)
                    .attributes(attributes)
                    .auctionPrice(auctionPrice)
                    .marketPrice(marketPrice)
                    .states(states)
                    .petclass(petclass)
                    .description(description)
                    .owner(owner)
                    .history(historyList)
                    .lotteryDatetime(lotteryDatetimeList)
                    .build();
        }else if (doc.get("history")==null && doc.getList("lottery_history", String.class) != null){
            List<String> lottery_s = doc.getList("lottery_history", String.class);
            for (String s : lottery_s){
                LotteryDatetime lotteryDatetime = LotteryDatetime.builder().dateTime(
                        ZonedDateTime.parse(s)
                ).build();
                lotteryDatetimeList.add(lotteryDatetime);
            }
            return Pet.builder()
                    .id(id)
                    .title(title)
                    .imageUrl(imageUrl)
                    .jsonUri(jsonUri)
                    .prebid(prebid)
                    .attributes(attributes)
                    .auctionPrice(auctionPrice)
                    .marketPrice(marketPrice)
                    .states(states)
                    .petclass(petclass)
                    .description(description)
                    .owner(owner)
                    .lotteryDatetime(lotteryDatetimeList)
                    .build();
        } else {
            return Pet.builder()
                    .id(id)
                    .title(title)
                    .imageUrl(imageUrl)
                    .jsonUri(jsonUri)
                    .prebid(prebid)
                    .attributes(attributes)
                    .auctionPrice(auctionPrice)
                    .marketPrice(marketPrice)
                    .states(states)
                    .petclass(petclass)
                    .description(description)
                    .owner(owner)
                    .build();
        }
//        List<History> historyList = new ArrayList<>();
//        List<Object> rawHistoryList = doc.getList("history", Object.class);  // 获取原始数据列表
//        for (Object raw : rawHistoryList){
//            historyList.add((History) raw);
//        }


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
