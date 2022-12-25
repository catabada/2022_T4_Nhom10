package com.example.weatherapi.mapper;

import java.util.List;

public interface IMapper<T, Dto> {
    T toEntity(Dto dto) ;

    Dto toDto(T entity);

    List<T> toEntities(List<Dto> dtos);

    List<Dto> toDtos(List<T> entities);
}
