package com.product.manager.server.service.currency;

import com.product.manager.server.entity.Currency;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyRatesHolder {

    void saveCurrencyRates(final Map<String, BigDecimal> currencyRates);
    BigDecimal getRate(final Currency currency);
    boolean contains(final Currency currency);
}