package com.example.weatherapi.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public abstract class AbstractCustomRepository<T, Long> extends SimpleJpaRepository<T, Long> {
    protected JPAQueryFactory queryFactory;
    protected EntityManager entityManager;
    protected Class<T> domainClass;
    protected AbstractCustomRepository(final Class<T> domainClass, final EntityManager em) {
        super(domainClass, em);
        this.domainClass = domainClass;
        this.entityManager = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    private AbstractCustomRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

}
