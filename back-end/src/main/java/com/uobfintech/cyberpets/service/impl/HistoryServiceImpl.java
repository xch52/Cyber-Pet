package com.uobfintech.cyberpets.service.impl;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.uobfintech.cyberpets.entity.Auction;
import com.uobfintech.cyberpets.entity.LotteryHistory;
import com.uobfintech.cyberpets.repository.MongoDAO;
import com.uobfintech.cyberpets.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private MongoDAO mongoDAO;

    public List<LotteryHistory> findAllLotteryHistory() {
        MongoCollection<Document> collection = mongoDAO.getCollection("lottery_history");
        List<Document> documents = new ArrayList<>();
        List<LotteryHistory> lotteryHistories = new ArrayList<>();
        // 查询所有文档
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                documents.add(doc);
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        for (Document doc : documents) {
            if (doc.getString("datetime") != null) {
                LotteryHistory lotteryHistory = LotteryHistory.builder()
                        .requester(doc.getString("requester"))
                        .requestId(doc.getString("request_id"))
                        .dateTime(ZonedDateTime.parse(doc.getString("datetime"), formatter))
                        .amount(doc.getInteger("amount"))
                        .tokenIds(doc.getList("token_ids", Integer.class)).build();
                lotteryHistories.add(lotteryHistory);
            }
        }
        return lotteryHistories;
    }

    public List<Auction> findAllAuction() {
        MongoCollection<Document> collection = mongoDAO.getCollection("auction_history");
        List<Document> documents = new ArrayList<>();
        List<Auction> auctions = new ArrayList<>();
        // 查询所有文档
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                documents.add(doc);
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        for (Document doc : documents) {
            Auction auction = Auction.builder()
                    .tokenId(doc.getInteger("token_id"))
                    .seller(doc.getString("seller"))
                    .startTime(ZonedDateTime.parse(doc.getString("start_time"), formatter))
                    .endTime(ZonedDateTime.parse(doc.getString("end_time"), formatter))
                    .timestamp(BigInteger.valueOf(Long.parseLong(doc.getString("timestamp"))))
                    .highestBid(doc.getDouble("highest_bid"))
                    .highestBidder(doc.getString("highest_bidder"))
                    .reservePrice(doc.getDouble("reserve_price"))
                    .build();
            auctions.add(auction);
        }
        return auctions;
    }

}
