package com.example.weatherapi.dto.datetime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeSearch {
    private int day;
    private int month;
    private int year;

    public DateTimeSearch nextDate() {
        DateTimeSearch nextDate = new DateTimeSearch(day, month, year);
        if (day == 31 && month == 12) {
            nextDate.setDay(1);
            nextDate.setMonth(1);
            nextDate.setYear(year + 1);
        } else if (day == 31) {
            nextDate.setDay(1);
            nextDate.setMonth(month + 1);
        } else if (day == 30 && (month == 4 || month == 6 || month == 9 || month == 11)) {
            nextDate.setDay(1);
            nextDate.setMonth(month + 1);
        } else if (day == 29 && month == 2) {
            nextDate.setDay(1);
            nextDate.setMonth(month + 1);
        } else if (day == 28 && month == 2 && year % 4 != 0) {
            nextDate.setDay(1);
            nextDate.setMonth(month + 1);
        } else {
            nextDate.setDay(day + 1);
            nextDate.setMonth(month);
        }
        return nextDate;

    }
}
