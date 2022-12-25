package com.example.weatherapi.repository.impl.province;

import com.example.weatherapi.constant.StatusData;
import com.example.weatherapi.dto.province.ProvinceSearch;
import com.example.weatherapi.repository.entity.Province;
import com.example.weatherapi.repository.entity.QProvince;
import com.example.weatherapi.repository.impl.AbstractCustomRepository;
import com.example.weatherapi.repository.province.ProvinceCustomRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class ProvinceCustomRepositoryImpl extends AbstractCustomRepository<Province, Long> implements ProvinceCustomRepository {

    public static final QProvince qProvince = QProvince.province;

    protected ProvinceCustomRepositoryImpl(EntityManager em) {
        super(Province.class, em);
    }

    @Override
    public List<Province> getProvinceSearch(ProvinceSearch search) {
        BooleanBuilder builder = buildConditionProvince(search);
        return queryFactory.selectFrom(qProvince)
                .where(builder)
                .fetch();
    }

    @Override
    public Optional<Province> getProvinceByCodeName(String codeName) {
        return Optional.ofNullable(queryFactory.selectFrom(qProvince)
                .where(qProvince.codeName.eq(codeName))
                .fetchOne());
    }

    public BooleanBuilder buildConditionProvince(ProvinceSearch provinceSearch) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(provinceSearch.getCodeName())) {
            builder.and(qProvince.codeName.toLowerCase().eq(provinceSearch.getCodeName().toLowerCase()));
        }
        if (!ObjectUtils.isEmpty(provinceSearch.getName())) {
            builder.and(qProvince.name.toLowerCase().startsWith(provinceSearch.getName().toLowerCase()));
        }
        return builder;
    }
}
