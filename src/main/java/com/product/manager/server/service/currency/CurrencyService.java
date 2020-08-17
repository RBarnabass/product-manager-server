package com.product.manager.server.service.currency;

import com.product.manager.server.entity.Currency;

import java.math.BigDecimal;

/**
 * Currency service provide specific operations with currency and rates
 */
public interface CurrencyService {

    /**
     * Update all currency rates by call an external API and update rates in memory holder
     * could be used in scheduler
     */
    void updateCurrencyRates();

    /**
     * Retrieves currency from storage
     * @param name currency symbol
     * @return stored currency
     */
    Currency getByName(final String name);

    /**
     * Retrieves rate by specified currency
     * @param currency model
     * @return rate by currency
     */
    BigDecimal getRate(final Currency currency);
}
