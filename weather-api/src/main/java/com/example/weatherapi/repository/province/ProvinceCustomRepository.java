package com.example.weatherapi.repository.province;

import com.example.weatherapi.dto.province.ProvinceSearch;
import com.example.weatherapi.repository.ICustomRepository;
import com.example.weatherapi.repository.entity.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceCustomRepository extends ICustomRepository<Province, Long> {
    List<Province> getProvinceSearch(ProvinceSearch search);

    Optional<Province> getProvinceByCodeName(String codeName);
}
