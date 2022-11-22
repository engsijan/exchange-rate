package com.sijan.exchange.rate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponseDto {
    private String message;
    private String timestamp;
}
