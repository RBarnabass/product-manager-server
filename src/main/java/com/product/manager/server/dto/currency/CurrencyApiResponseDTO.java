package com.product.manager.server.dto.currency;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyApiResponseDTO {

    private boolean success;
    private int timestamp;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
