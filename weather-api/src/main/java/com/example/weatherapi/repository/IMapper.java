package com.example.weatherapi.repository;

import com.example.weatherapi.dto.weather.WeatherDto;
import com.example.weatherapi.repository.entity.Weather;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


public interface IMapper<T, Dto> {
    T toEntity(Dto dto);
    Dto toDto(T entity);

    List<T> toEntities(List<Dto> dtos);

    List<Dto> toDtos(List<T> entities);
}
