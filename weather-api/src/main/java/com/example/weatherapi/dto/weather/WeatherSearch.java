package com.example.weatherapi.dto.weather;

import com.example.weatherapi.dto.datetime.DateTimeDto;
import com.example.weatherapi.dto.datetime.DateTimeSearch;
import com.example.weatherapi.dto.province.ProvinceSearch;
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
public class WeatherSearch {
    private String naturalKey;
    private DateTimeSearch date;
    private String time;
    private ProvinceSearch province;
}
