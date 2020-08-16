package com.product.manager.server.service.currency.impl;

import com.product.manager.server.entity.Currency;
import com.product.manager.server.service.currency.CurrencyRatesHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CurrencyRatesHolderImpl implements CurrencyRatesHolder {

    private final Map<String, BigDecimal> rates = new ConcurrentHashMap<>();

    @Override
    public void saveCurrencyRates(final Map<String, BigDecimal> currencyRates) {
        rates.putAll(currencyRates);
    }

    @Override
    public BigDecimal getRate(final Currency currency) {
        return rates.get(currency.getName());
    }

    @Override
    public boolean contains(final Currency currency) {
        return rates.containsKey(currency.getName().toLowerCase());
    }
}
