package com.example.weatherapi.mapper.province;

import com.example.weatherapi.dto.province.ProvinceDto;
import com.example.weatherapi.repository.IMapper;
import com.example.weatherapi.repository.entity.Province;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component("provinceMapper")
public class ProvinceMapper implements IMapper<Province, ProvinceDto> {

    @Override
    public Province toEntity(ProvinceDto provinceDto) {
        if (provinceDto == null) {
            return null;
        }

        Province.ProvinceBuilder<?, ?> province = Province.builder();

        province.id(provinceDto.getId());
        province.name(provinceDto.getName());
        province.codeName(provinceDto.getCodeName());

        return province.build();
    }

    @Override
    public ProvinceDto toDto(Province province) {
        if (province == null) {
            return null;
        }

        ProvinceDto.ProvinceDtoBuilder<?, ?> provinceDto = ProvinceDto.builder();

        provinceDto.id(province.getId());
        provinceDto.name(province.getName());
        provinceDto.codeName(province.getCodeName());

        return provinceDto.build();
    }

    @Override
    public List<Province> toEntities(List<ProvinceDto> provinceDtoList) {
        if (provinceDtoList == null) {
            return null;
        }

        List<Province> list = new ArrayList<Province>(provinceDtoList.size());
        for (ProvinceDto provinceDto : provinceDtoList) {
            list.add(toEntity(provinceDto));
        }

        return list;
    }

    @Override
    public List<ProvinceDto> toDtos(List<Province> provinceList) {
        if (provinceList == null) {
            return null;
        }

        List<ProvinceDto> list = new ArrayList<ProvinceDto>(provinceList.size());
        for (Province province : provinceList) {
            list.add(toDto(province));
        }

        return list;
    }
}
