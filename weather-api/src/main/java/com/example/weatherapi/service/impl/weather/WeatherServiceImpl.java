package com.example.weatherapi.service.impl.weather;

import com.example.weatherapi.constant.StatusData;
import com.example.weatherapi.dto.weather.WeatherSearch;
import com.example.weatherapi.handle.exception.NotFoundException;
import com.example.weatherapi.handle.result.DataResult;
import com.example.weatherapi.mapper.weather.WeatherMapper;
import com.example.weatherapi.repository.entity.Weather;
import com.example.weatherapi.repository.weather.WeatherCustomRepository;
import com.example.weatherapi.service.weather.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final WeatherCustomRepository weatherCustomRepository;
    private final WeatherMapper weatherMapper;

    @Autowired
    public WeatherServiceImpl(WeatherCustomRepository weatherCustomRepository, WeatherMapper weatherMapper) {
        this.weatherCustomRepository = weatherCustomRepository;
        this.weatherMapper = weatherMapper;
    }


    @Override
    public DataResult getWeathers(WeatherSearch weatherSearch) {
        return DataResult.success(weatherMapper.toDtos(weatherCustomRepository.getWeatherSearch(weatherSearch))).build();
    }

    @Override
    public DataResult getWeatherByTimeAndCodeProvince(final WeatherSearch weatherSearch) {
        Optional<Weather> optional = weatherCustomRepository.getWeatherByTimeAndCodeProvince(weatherSearch);
        if (optional.isPresent()) {
            return DataResult.success(weatherMapper.toDto(optional.get())).build();
        } else {
            throw new NotFoundException("Weather not found");
        }
    }

}
