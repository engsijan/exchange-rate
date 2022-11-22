package com.sijan.exchange.rate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateData {
    private String no;
    private String effectiveDate;
    private double bid;
    private double ask;
    private double mid;
}