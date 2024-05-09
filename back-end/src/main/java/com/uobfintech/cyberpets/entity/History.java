package com.uobfintech.cyberpets.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
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
}
