package com.example.weatherapi.controller;

import com.example.weatherapi.dto.weather.WeatherSearch;
import com.example.weatherapi.handle.response.HttpResponse;
import com.example.weatherapi.handle.response.HttpResponseError;
import com.example.weatherapi.handle.response.HttpResponseSuccess;
import com.example.weatherapi.handle.result.DataResult;
import com.example.weatherapi.service.weather.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/weather")
@CrossOrigin(origins = "*")
@Slf4j
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> getWeatherSearch(@RequestBody WeatherSearch weatherSearch) {
        log.info(weatherSearch.getTime());
        DataResult result = weatherService.getWeathers(weatherSearch);
        return result.getSuccess() ? ResponseEntity.ok(HttpResponseSuccess.success(result.getData()).build())
                : ResponseEntity.badRequest().body(HttpResponseError.error(result.getHttpStatus(), result.getMessage()).build());
    }

    @RequestMapping("/province")
    public ResponseEntity<HttpResponse> getWeatherByCodeAndTime(@RequestBody WeatherSearch weatherSearch) {
        DataResult result = weatherService.getWeatherByTimeAndCodeProvince(weatherSearch);
        return result.getSuccess() ? ResponseEntity.ok(HttpResponseSuccess.success(result.getData()).build())
                : ResponseEntity.badRequest().body(HttpResponseError.error(result.getHttpStatus(), result.getMessage()).build());
    }
}
