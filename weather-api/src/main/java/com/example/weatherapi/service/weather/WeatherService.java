package com.example.weatherapi.service.weather;

import com.example.weatherapi.dto.weather.WeatherSearch;
import com.example.weatherapi.handle.result.DataResult;

public interface WeatherService {
    DataResult getWeathers(final WeatherSearch weatherSearch);

    DataResult getWeatherByTimeAndCodeProvince(final WeatherSearch weatherSearch);


}
