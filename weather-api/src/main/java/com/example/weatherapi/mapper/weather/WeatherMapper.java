package com.example.weatherapi.mapper.weather;

import com.example.weatherapi.dto.datetime.DateTimeDto;
import com.example.weatherapi.dto.province.ProvinceDto;
import com.example.weatherapi.dto.weather.WeatherDto;
import com.example.weatherapi.mapper.datetime.DateTimeMapper;
import com.example.weatherapi.mapper.province.ProvinceMapper;
import com.example.weatherapi.repository.IMapper;
import com.example.weatherapi.repository.entity.DateTime;
import com.example.weatherapi.repository.entity.Province;
import com.example.weatherapi.repository.entity.Weather;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("weatherMapper")
@Mapper(componentModel = "spring")
@Slf4j
public class WeatherMapper implements IMapper<Weather, WeatherDto> {
    private final DateTimeMapper dateTimeMapper;
    private final ProvinceMapper provinceMapper;

    @Autowired
    public WeatherMapper(DateTimeMapper dateTimeMapper, ProvinceMapper provinceMapper) {
        this.dateTimeMapper = dateTimeMapper;
        this.provinceMapper = provinceMapper;
    }

    @Override
    public Weather toEntity(WeatherDto dto) {
        if ( dto == null ) {
            return null;
        }

        Weather.WeatherBuilder<?, ?> weather = Weather.builder();

        weather.id( dto.getId() );
        weather.naturalKey( dto.getNaturalKey() );
        weather.dateTime( dateTimeMapper.toEntity( dto.getDateTime() ) );
        weather.time( dto.getTime() );
        weather.province( provinceMapper.toEntity( dto.getProvince() ) );
        weather.temp( dto.getTemp() );
        weather.cloudDescription( dto.getCloudDescription() );
        weather.minTemp( dto.getMinTemp() );
        weather.maxTemp( dto.getMaxTemp() );
        weather.humidity( dto.getHumidity() );
        weather.vision( dto.getVision() );
        weather.windSpd( dto.getWindSpd() );
        weather.uvIndex( dto.getUvIndex() );
        weather.atmosphereQuality( dto.getAtmosphereQuality() );
        weather.status( dto.getStatus() );
        weather.expiredDate( dto.getExpiredDate() );

        return weather.build();
    }

    @Override
    public WeatherDto toDto(Weather entity) {
        if ( entity == null ) {
            return null;
        }

        WeatherDto.WeatherDtoBuilder<?, ?> weatherDto = WeatherDto.builder();

        weatherDto.id( entity.getId() );
        weatherDto.naturalKey( entity.getNaturalKey() );
        weatherDto.dateTime( dateTimeMapper.toDto(entity.getDateTime()) );
        weatherDto.time( entity.getTime() );
        weatherDto.province( provinceMapper.toDto(entity.getProvince()) );
        weatherDto.temp( entity.getTemp() );
        weatherDto.cloudDescription( entity.getCloudDescription() );
        weatherDto.minTemp( entity.getMinTemp() );
        weatherDto.maxTemp( entity.getMaxTemp() );
        weatherDto.humidity( entity.getHumidity() );
        weatherDto.vision( entity.getVision() );
        weatherDto.windSpd( entity.getWindSpd() );
        weatherDto.uvIndex( entity.getUvIndex() );
        weatherDto.atmosphereQuality( entity.getAtmosphereQuality() );
        weatherDto.status( entity.getStatus() );
        weatherDto.expiredDate( entity.getExpiredDate() );

        return weatherDto.build();
    }

    @Override
    public List<Weather> toEntities(List<WeatherDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Weather> list = new ArrayList<Weather>( dtos.size() );
        for ( WeatherDto weatherDto : dtos ) {
            list.add( toEntity( weatherDto ) );
        }

        return list;
    }

    @Override
    public List<WeatherDto> toDtos(List<Weather> entities) {
        if ( entities == null ) {
            return null;
        }

        List<WeatherDto> list = new ArrayList<WeatherDto>( entities.size() );
        for ( Weather weather : entities ) {
            list.add( toDto( weather ) );
        }

        return list;
    }



}
