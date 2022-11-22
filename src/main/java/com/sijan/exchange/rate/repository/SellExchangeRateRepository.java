package com.sijan.exchange.rate.repository;

import com.sijan.exchange.rate.entity.SellExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellExchangeRateRepository extends JpaRepository<SellExchangeRateEntity, Integer> {
    Optional<SellExchangeRateEntity> findByCodeAndEffectiveDate(String code, String effectiveDate);
}
