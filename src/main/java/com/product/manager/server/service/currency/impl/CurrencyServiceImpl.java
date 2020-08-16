package com.product.manager.server.service.currency.impl;

import com.product.manager.server.entity.Currency;
import com.product.manager.server.exception.DataNotFoundException;
import com.product.manager.server.repository.CurrencyRepository;
import com.product.manager.server.service.currency.CurrencyService;
import com.product.manager.server.service.currency.CurrencyRatesHolder;
import com.product.manager.server.service.currency.CurrencyProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.product.manager.server.exception.api.ExceptionCode.CURRENCY_NOT_FOUND;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRatesHolder currencyHolder;
    private final CurrencyProvider currencyProvider;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(final CurrencyRatesHolder currencyHolder,
                               final CurrencyProvider currencyProvider,
                               final CurrencyRepository currencyRepository) {
        this.currencyHolder = currencyHolder;
        this.currencyProvider = currencyProvider;
        this.currencyRepository = currencyRepository;
    }


    @PostConstruct
    protected void initCurrencyStore() {
        final Map<String, BigDecimal> currencyRates = currencyProvider.getCurrencyRates();
        currencyHolder.saveCurrencyRates(currencyRates);
        currencyRepository.saveAll(buildCurrencyModels(currencyRates));
    }

    @Override
    public void updateCurrencyRates() {
        final Map<String, BigDecimal> currencyRates = currencyProvider.getCurrencyRates();
        currencyHolder.saveCurrencyRates(currencyRates);
    }

    @Override
    public Currency getByName(final String name) {
        return currencyRepository.findByName(name).orElseThrow(() -> {
            throw new DataNotFoundException("Currency does not exist.", CURRENCY_NOT_FOUND);
        });
    }

    @Override
    public BigDecimal getRate(final Currency currency) {
        return currencyHolder.getRate(currency);
    }

    private List<Currency> buildCurrencyModels(final Map<String, BigDecimal> currencyRates) {
        return currencyRates.keySet().stream()
                .map(Currency::new)
                .collect(Collectors.toList());
    }
}
