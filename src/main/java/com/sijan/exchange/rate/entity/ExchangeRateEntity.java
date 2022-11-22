package com.sijan.exchange.rate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class ExchangeRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "code", length = 100, nullable = false)
    private String code;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "effective_date",nullable = false)
    private String effectiveDate;
}
