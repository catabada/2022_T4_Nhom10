package com.example.weatherapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICustomRepository<T, Long> extends JpaRepository<T, Long> {
}
