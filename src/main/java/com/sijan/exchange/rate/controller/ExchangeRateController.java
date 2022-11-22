package com.sijan.exchange.rate.controller;

import com.sijan.exchange.rate.dto.ExchangeRateDto;
import com.sijan.exchange.rate.dto.PurchasingCostRequest;
import com.sijan.exchange.rate.dto.PurchasingCostResponse;
import com.sijan.exchange.rate.exception.NotFoundException;
import com.sijan.exchange.rate.service.MiddleExchangeRateService;
import com.sijan.exchange.rate.service.SellExchangeRateService;
import com.sijan.exchange.rate.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final SellExchangeRateService sellExchangeRateService;
    private final MiddleExchangeRateService middleExchangeRateService;

    @GetMapping("/{code}")
    public ResponseEntity<ExchangeRateDto> getSellExchangeRate(@PathVariable String code,
                                                               @RequestParam(required = false) String effectiveDate) {
        if (!StringUtils.hasText(effectiveDate))
            effectiveDate = DateUtils.getCurrentDate();

        if (!DateUtils.isValid(effectiveDate))
            throw new IllegalArgumentException("Invalid Date Format");
        Optional<ExchangeRateDto> optionalSellExchangeRate = sellExchangeRateService.getSellExchangeRate(code, effectiveDate);
        if (optionalSellExchangeRate.isEmpty())
            throw new NotFoundException("Cannot find sell exchange data related to " + code);

        return ResponseEntity.ok(optionalSellExchangeRate.get());

    }

    @PostMapping("/calculate-total-purchasing-cost")
    public ResponseEntity<PurchasingCostResponse> calculate(@RequestBody PurchasingCostRequest request) {
        request.validate();
        double totalCost = middleExchangeRateService.getTotalPurchasingCost(request.getCodes(), StringUtils.hasText(request.getEffectiveDate()) ? request.getEffectiveDate() : DateUtils.getCurrentDate());
        return ResponseEntity.ok(new PurchasingCostResponse(totalCost));
    }
}
