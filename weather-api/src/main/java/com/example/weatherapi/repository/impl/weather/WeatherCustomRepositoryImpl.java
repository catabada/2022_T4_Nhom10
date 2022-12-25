package com.example.weatherapi.repository.impl.weather;

import com.example.weatherapi.constant.StatusData;
import com.example.weatherapi.dto.datetime.DateTimeSearch;
import com.example.weatherapi.dto.weather.WeatherSearch;
import com.example.weatherapi.repository.entity.QWeather;
import com.example.weatherapi.repository.entity.Weather;
import com.example.weatherapi.repository.impl.AbstractCustomRepository;
import com.example.weatherapi.repository.weather.WeatherCustomRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class WeatherCustomRepositoryImpl extends AbstractCustomRepository<Weather, Long> implements WeatherCustomRepository {
    public static QWeather qWeather = QWeather.weather;

    protected WeatherCustomRepositoryImpl(EntityManager em) {
        super(Weather.class, em);
    }

    @Override
    public List<Weather> getWeatherSearch(WeatherSearch search) {
        BooleanBuilder builder = buildConditionWeather(search);
        return queryFactory.selectFrom(qWeather)
                .where(builder)
                .orderBy(qWeather.dateTime.date.asc(), qWeather.time.asc())
                .fetch();
    }


    @Override
    public Optional<Weather> getWeatherByTimeAndCodeProvince(WeatherSearch search) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(search.getProvince())) {
            if (!ObjectUtils.isEmpty(search.getProvince().getCodeName())) {
                builder.and(qWeather.province.codeName.toLowerCase().eq(search.getProvince().getCodeName().toLowerCase()));
            }
            if (!ObjectUtils.isEmpty(search.getProvince().getName())) {
                builder.and(qWeather.province.name.toLowerCase().contains(search.getProvince().getName().toLowerCase()));
            }
        }
        if (!ObjectUtils.isEmpty(search.getDate())) {
            if (!ObjectUtils.isEmpty(search.getDate().getDay())) {
                builder.and(qWeather.dateTime.dayOfMonth.eq(search.getDate().getDay()));
            }
            if (!ObjectUtils.isEmpty(search.getDate().getMonth())) {
                builder.and(qWeather.dateTime.monthOfYear.eq(search.getDate().getMonth()));
            }
            if (!ObjectUtils.isEmpty(search.getDate().getYear())) {
                builder.and(qWeather.dateTime.year.eq(search.getDate().getYear()));
            }
        }
        if (!ObjectUtils.isEmpty(search.getTime())) {
            builder.and(qWeather.time.startsWith(search.getTime()));
        }
        builder.and(qWeather.atmosphereQuality.ne("Chưa rõ\r"));
        builder.and(qWeather.status.eq(StatusData.UPDATED));
        return Optional.ofNullable(queryFactory.selectFrom(qWeather)
                .where(builder)
                .fetchOne());
    }

    private BooleanBuilder buildConditionWeather(WeatherSearch search) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(search.getProvince())) {
            if (!ObjectUtils.isEmpty(search.getProvince().getCodeName())) {
                builder.and(qWeather.province.codeName.toLowerCase().eq(search.getProvince().getCodeName().toLowerCase()));
            }
        }
        if (!ObjectUtils.isEmpty(search.getDate())) {
            DateTimeSearch nextDateTimeSearch = search.getDate().nextDate();
            String date = search.getDate().getDay() + "/" + search.getDate().getMonth() + "/" + search.getDate().getYear();
            String nextDate = nextDateTimeSearch.getDay() + "/" + nextDateTimeSearch.getMonth() + "/" + nextDateTimeSearch.getYear();

            builder.and(qWeather.dateTime.date.eq(date).or(qWeather.dateTime.date.eq(nextDate)));
            if (!ObjectUtils.isEmpty(search.getTime())) {
                builder.and(qWeather.dateTime.date.eq(date).and(qWeather.time.substring(0, 2).gt(search.getTime())).or(qWeather.dateTime.date.eq(nextDate)));
            }
        }
        builder.and(qWeather.status.eq(StatusData.UPDATED));
        return builder;
    }


}
