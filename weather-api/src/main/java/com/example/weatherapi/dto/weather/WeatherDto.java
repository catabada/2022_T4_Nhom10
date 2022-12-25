package com.example.weatherapi.dto.weather;

import com.example.weatherapi.constant.StatusData;
import com.example.weatherapi.dto.datetime.DateTimeDto;
import com.example.weatherapi.dto.province.ProvinceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WeatherDto {
    private Long id;
    private String naturalKey;
    private DateTimeDto dateTime;
    private String time;
    private ProvinceDto province;
    private int temp;
    private String cloudDescription;
    private int minTemp;
    private int maxTemp;
    private double humidity;
    private double vision;
    private double windSpd;
    private double uvIndex;
    private String atmosphereQuality;
    private StatusData status;
    private String expiredDate;
}
