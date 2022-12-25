package com.example.weatherapi.mapper.datetime;

import com.example.weatherapi.dto.datetime.DateTimeDto;
import com.example.weatherapi.repository.IMapper;
import com.example.weatherapi.repository.entity.DateTime;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component("dateTimeMapper")
public class DateTimeMapper implements IMapper<DateTime, DateTimeDto> {

    @Override
    public DateTime toEntity(DateTimeDto dto) {
        if ( dto == null ) {
            return null;
        }

        DateTime.DateTimeBuilder<?, ?> dateTime = DateTime.builder();

        dateTime.id( dto.getId() );
        dateTime.date( dto.getDate() );
        dateTime.year( dto.getYear() );
        dateTime.dayOfMonth( dto.getDayOfMonth() );
        dateTime.month( dto.getMonth() );
        dateTime.monthOfYear( dto.getMonthOfYear() );
        dateTime.dayOfYear( dto.getDayOfYear() );
        dateTime.day( dto.getDay() );
        dateTime.dayOfWeek( dto.getDayOfWeek() );
        dateTime.weekend( dto.getWeekend() );
        dateTime.weekOfYear( dto.getWeekOfYear() );
        dateTime.quarter( dto.getQuarter() );
        dateTime.previousDay( dto.getPreviousDay() );
        dateTime.nextDay( dto.getNextDay() );

        return dateTime.build();
    }

    @Override
    public DateTimeDto toDto(DateTime dateTime) {
        if ( dateTime == null ) {
            return null;
        }

        DateTimeDto.DateTimeDtoBuilder<?, ?> dateTimeDto = DateTimeDto.builder();

        dateTimeDto.id( dateTime.getId() );
        dateTimeDto.date( dateTime.getDate() );
        dateTimeDto.year( dateTime.getYear() );
        dateTimeDto.dayOfMonth( dateTime.getDayOfMonth() );
        dateTimeDto.month( dateTime.getMonth() );
        dateTimeDto.monthOfYear( dateTime.getMonthOfYear() );
        dateTimeDto.dayOfYear( dateTime.getDayOfYear() );
        dateTimeDto.day( dateTime.getDay() );
        dateTimeDto.dayOfWeek( dateTime.getDayOfWeek() );
        dateTimeDto.weekend( dateTime.getWeekend() );
        dateTimeDto.weekOfYear( dateTime.getWeekOfYear() );
        dateTimeDto.quarter( dateTime.getQuarter() );
        dateTimeDto.previousDay( dateTime.getPreviousDay() );
        dateTimeDto.nextDay( dateTime.getNextDay() );

        return dateTimeDto.build();
    }

    @Override
    public List<DateTime> toEntities(List<DateTimeDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<DateTime> list = new ArrayList<DateTime>( dtos.size() );
        for ( DateTimeDto dateTimeDto : dtos ) {
            list.add( toEntity( dateTimeDto ) );
        }

        return list;
    }

    @Override
    public List<DateTimeDto> toDtos(List<DateTime> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DateTimeDto> list = new ArrayList<DateTimeDto>( entities.size() );
        for ( DateTime dateTime : entities ) {
            list.add( toDto( dateTime ) );
        }

        return list;
    }
}
