package com.sijan.exchange.rate.client;

import com.sijan.exchange.rate.dto.ExchangeRateData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange-rate-client", url = "http://api.nbp.pl/api/exchangerates/rates")
public interface CurrencyExchangeRateClient {
    @GetMapping("/c/{code}/today?format=json")
    ExchangeRateData getSellingExchangeRateOfToday(@PathVariable String code);

    @GetMapping("/c/{code}/{effectiveDate}?format=json")
    ExchangeRateData getSellingExchangeRateOnSpecificDate(@PathVariable String code,
                                                          @PathVariable String effectiveDate);

    @GetMapping("/a/{code}/{effectiveDate}?format=json")
    ExchangeRateData getMiddleExchangeRateOnSpecificDate(@PathVariable String code,
                                                         @PathVariable String effectiveDate);
}
