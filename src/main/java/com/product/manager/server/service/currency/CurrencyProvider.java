package com.product.manager.server.service.currency;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Currency provider retrieves rates from external API
 */
public interface CurrencyProvider {

    /**
     * Get fresh rates
     * @return map with currency symbol and rate
     */
    Map<String, BigDecimal> getCurrencyRates();
}
