package com.example.carlocation.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateAndTime {
    private LocalDateTime dateTime;

    public DateAndTime() {
        this.dateTime = LocalDateTime.now();
    }
    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = this.dateTime.format(formatter);
        return formattedDateTime;
    }
    public long getTime(){
        long millis = System.currentTimeMillis();
        return millis;
    }

}
