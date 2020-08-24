package com.product.manager.server.service.currency;

import com.product.manager.server.entity.Currency;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Currency rates holder for keep in memory last rates update
 */
public interface CurrencyRatesHolder {

    /**
     * save or update currency rates
     * @param currencyRates new rates
     */
    void saveCurrencyRates(final Map<String, BigDecimal> currencyRates);

    /**
     * Retrieves rate by specified currency
     * @param currency model
     * @return stored rate
     */
    BigDecimal getRate(final Currency currency);

    /**
     * Useful for validation if we have to convert currencies
     * @param currency currency to exchange
     * @return true if rate exist
     */
    boolean contains(final Currency currency);
}
