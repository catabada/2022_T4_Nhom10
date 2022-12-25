package com.example.weatherapi.service.province;

import com.example.weatherapi.dto.province.ProvinceSearch;
import com.example.weatherapi.handle.result.DataResult;

public interface ProvinceService {
    DataResult getProvinceByCode(String codeName);

    DataResult getProvincesSearch(ProvinceSearch search);
}
