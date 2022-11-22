package com.sijan.exchange.rate.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "sell_exchange_rate", indexes = @Index(name = "code_effective_date_sell_idx", columnList = "effective_date, code"))
public class SellExchangeRateEntity extends ExchangeRateEntity {
}
