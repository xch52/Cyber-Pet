package com.uobfintech.cyberpets.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Integer tokenId;

    // private String image;

    // private String title;

    private String buyerId;

    private String sellerId;

    private ZonedDateTime dateTime;

    private Double price;

    private String type;

    public String getFormattedDateTime() {
        return dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public void setFormattedDateTime(String dateTime) {
        this.dateTime = ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static List<History> convertToListOfHistory(List<Map<String, Object>> rawList) {
        List<History> historyList = new ArrayList<>();
        for (Map<String, Object> item : rawList) {
            History history = new History();
            history.setBuyerId((String) item.get("buyerId"));
            history.setDateTime(ZonedDateTime.parse((String) item.get("dateTime")));
            history.setPrice((Double) item.get("price"));
            history.setSellerId((String) item.get("sellerId"));
            history.setType((String) item.get("type"));
            historyList.add(history);
        }
        return historyList;
    }
}
