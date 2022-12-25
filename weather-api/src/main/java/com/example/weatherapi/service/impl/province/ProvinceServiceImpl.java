package com.example.weatherapi.service.impl.province;

import com.example.weatherapi.dto.province.ProvinceSearch;
import com.example.weatherapi.handle.exception.NotFoundException;
import com.example.weatherapi.handle.result.DataResult;
import com.example.weatherapi.mapper.province.ProvinceMapper;
import com.example.weatherapi.repository.entity.Province;
import com.example.weatherapi.repository.province.ProvinceCustomRepository;
import com.example.weatherapi.service.province.ProvinceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceCustomRepository provinceCustomRepository;
    private final ProvinceMapper provinceMapper;

    @Autowired
    public ProvinceServiceImpl(ProvinceCustomRepository provinceCustomRepository, ProvinceMapper provinceMapper) {
        this.provinceCustomRepository = provinceCustomRepository;
        this.provinceMapper = provinceMapper;
    }

    @Override
    public DataResult getProvinceByCode(String codeName) {
        Optional<Province> province = provinceCustomRepository.getProvinceByCodeName(codeName);
        if (province.isPresent()) {
            return DataResult.success(provinceMapper.toDto(province.get())).build();
        } else {
            throw new NotFoundException("Not found!");
        }
    }

    @Override
    public DataResult getProvincesSearch(ProvinceSearch search) {
        return DataResult.success(provinceMapper.toDtos(provinceCustomRepository.getProvinceSearch(search))).build();
    }
}
