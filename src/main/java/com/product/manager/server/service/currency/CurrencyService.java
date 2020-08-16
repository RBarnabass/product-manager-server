package com.product.manager.server.service.currency;

import com.product.manager.server.entity.Currency;

import java.math.BigDecimal;

public interface CurrencyService {

    void updateCurrencyRates();
    Currency getByName(final String name);
    BigDecimal getRate(final Currency currency);
}
