package com.example.weatherapi.repository.weather;

import com.example.weatherapi.dto.weather.WeatherSearch;
import com.example.weatherapi.repository.ICustomRepository;
import com.example.weatherapi.repository.entity.Weather;

import java.util.List;
import java.util.Optional;


public interface WeatherCustomRepository extends ICustomRepository<Weather, Long> {
    List<Weather> getWeatherSearch(WeatherSearch search);

    Optional<Weather> getWeatherByTimeAndCodeProvince(WeatherSearch search);

}
