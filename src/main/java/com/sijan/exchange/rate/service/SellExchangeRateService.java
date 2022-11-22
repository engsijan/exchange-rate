package com.sijan.exchange.rate.service;

import com.sijan.exchange.rate.client.CurrencyExchangeRateClient;
import com.sijan.exchange.rate.dto.ExchangeRateData;
import com.sijan.exchange.rate.dto.ExchangeRateDto;
import com.sijan.exchange.rate.entity.SellExchangeRateEntity;
import com.sijan.exchange.rate.exception.NotFoundException;
import com.sijan.exchange.rate.repository.SellExchangeRateRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellExchangeRateService {
    private final SellExchangeRateRepository sellExchangeRateRepository;
    private final CurrencyExchangeRateClient currencyExchangeRateClient;
    private final ModelMapper modelMapper;

    public Optional<ExchangeRateDto> getSellExchangeRate(String code, String effectiveDate) {
        Optional<SellExchangeRateEntity> optionalSellExchangeRate = sellExchangeRateRepository.findByCodeAndEffectiveDate(code, effectiveDate);
        if (optionalSellExchangeRate.isPresent())
            return Optional.ofNullable(modelMapper.map(optionalSellExchangeRate.get(), ExchangeRateDto.class));

        try {
            ExchangeRateData exchangeRate = currencyExchangeRateClient.getSellingExchangeRateOnSpecificDate(code, effectiveDate);
            if (exchangeRate.getRates().isEmpty()) {
                throw new NotFoundException("Cannot find sell exchange data related to " + code);
            }

            return Optional.of(this.save(new ExchangeRateDto(code, exchangeRate.getRates().get(0).getAsk(), effectiveDate)));
        } catch (FeignException e) {
            if (e.getMessage().contains("[404 Not Found]"))
                throw new NotFoundException("Cannot find sell exchange data related to " + code);

            log.error("Got exception while calling getSellingExchangeRateOfSpecificDate [{}]", e.getMessage());
            throw e;
        }
    }

    public ExchangeRateDto save(ExchangeRateDto dto) {
        SellExchangeRateEntity entity = modelMapper.map(dto, SellExchangeRateEntity.class);
        SellExchangeRateEntity savedEntity = sellExchangeRateRepository.save(entity);
        return modelMapper.map(savedEntity, ExchangeRateDto.class);
    }
}
