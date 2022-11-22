package com.sijan.exchange.rate.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExchangeRateData {
    private String table;
    private String currency;
    private String code;
    private List<RateData> rates;
}
