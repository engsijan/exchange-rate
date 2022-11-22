package com.sijan.exchange.rate.repository;

import com.sijan.exchange.rate.entity.MiddleExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MiddleExchangeRateRepository extends JpaRepository<MiddleExchangeRateEntity, Integer> {
    Optional<MiddleExchangeRateEntity> findByCodeAndEffectiveDate(String code, String effectiveDate);
}
