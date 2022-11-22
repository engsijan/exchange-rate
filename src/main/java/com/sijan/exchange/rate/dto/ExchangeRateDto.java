package com.sijan.exchange.rate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExchangeRateDto {
    private int id;
    private String code;
    private double price;
    private String effectiveDate;

    public ExchangeRateDto(String code, double price, String effectiveDate) {
        this.code = code;
        this.price = price;
        this.effectiveDate = effectiveDate;
    }
}
