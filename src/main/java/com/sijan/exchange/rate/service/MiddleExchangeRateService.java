package com.sijan.exchange.rate.service;

import com.sijan.exchange.rate.client.CurrencyExchangeRateClient;
import com.sijan.exchange.rate.dto.ExchangeRateData;
import com.sijan.exchange.rate.dto.ExchangeRateDto;
import com.sijan.exchange.rate.entity.MiddleExchangeRateEntity;
import com.sijan.exchange.rate.exception.NotFoundException;
import com.sijan.exchange.rate.repository.MiddleExchangeRateRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MiddleExchangeRateService {
    private final MiddleExchangeRateRepository repository;
    private final CurrencyExchangeRateClient currencyExchangeRateClient;
    private final ModelMapper modelMapper;

    public ExchangeRateDto getMiddleExchangeRate(String code, String effectiveDate) {
        Optional<MiddleExchangeRateEntity> optionalMiddleExchangeRate = repository.findByCodeAndEffectiveDate(code, effectiveDate);
        if (optionalMiddleExchangeRate.isPresent())
            return modelMapper.map(optionalMiddleExchangeRate.get(), ExchangeRateDto.class);

        try {
            ExchangeRateData exchangeRate = currencyExchangeRateClient.getMiddleExchangeRateOnSpecificDate(code, effectiveDate);
            if (exchangeRate.getRates().isEmpty()) {
                throw new NotFoundException("Cannot find middle exchange data related to " + code);
            }

            return this.save(new ExchangeRateDto(code, exchangeRate.getRates().get(0).getMid(), effectiveDate));
        } catch (FeignException e) {
            if (e.getMessage().contains("[404 Not Found]"))
                throw new NotFoundException("Cannot find middle exchange data related to " + code);

            log.error("Got exception while calling getMiddleExchangeRateOnSpecificDate [{}]", e.getMessage());
            throw e;
        }
    }

    public ExchangeRateDto save(ExchangeRateDto dto) {
        MiddleExchangeRateEntity entity = modelMapper.map(dto, MiddleExchangeRateEntity.class);
        MiddleExchangeRateEntity savedEntity = repository.save(entity);
        return modelMapper.map(savedEntity, ExchangeRateDto.class);
    }

    public double getTotalPurchasingCost(List<String> codes, String effectiveDate) {
        if (codes.isEmpty())
            return 0;

        return codes.stream().map(code -> getMiddleExchangeRate(code, effectiveDate))
                .map(ExchangeRateDto::getPrice)
                .reduce(0.0, Double::sum);
    }
}
