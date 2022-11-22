package com.sijan.exchange.rate.dto;

import com.sijan.exchange.rate.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Setter
public class PurchasingCostRequest {
    private List<String> codes;
    private String effectiveDate;

    public void validate() {
        if (StringUtils.hasText(effectiveDate) && !DateUtils.isValid(effectiveDate))
            throw new IllegalArgumentException("Invalid date format");

        if (codes.isEmpty())
            throw new IllegalArgumentException("Currency code list empty");
    }
}
