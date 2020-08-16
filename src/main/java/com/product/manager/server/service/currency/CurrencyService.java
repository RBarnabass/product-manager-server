package com.product.manager.server.service.currency;

import com.product.manager.server.entity.Currency;

public interface CurrencyService {

    void updateCurrencyRates();
    Currency getByName(final String name);
}
