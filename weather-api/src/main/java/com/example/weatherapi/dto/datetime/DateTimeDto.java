package com.example.weatherapi.dto.datetime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DateTimeDto {
    private Long id;

    private String date;
    private int year;
    private String month;
    private int dayOfMonth;
    private int monthOfYear;
    private int dayOfYear;
    private String day;
    private int dayOfWeek;
    private String weekend;
    private int getDayOfYear;
    private int weekOfYear;
    private int quarter;
    private String previousDay;
    private String nextDay;
}
