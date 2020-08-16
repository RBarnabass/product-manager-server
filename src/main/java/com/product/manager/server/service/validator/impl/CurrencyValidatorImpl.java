package com.product.manager.server.service.validator.impl;

import com.product.manager.server.entity.Currency;
import com.product.manager.server.exception.DataNotFoundException;
import com.product.manager.server.repository.CurrencyRepository;
import com.product.manager.server.service.currency.CurrencyRatesHolder;
import com.product.manager.server.service.validator.CurrencyValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import static com.product.manager.server.exception.api.ExceptionCode.*;

@Slf4j
@Service
public class CurrencyValidatorImpl implements CurrencyValidator {

    private final CurrencyRatesHolder currencyRatesHolder;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyValidatorImpl(final CurrencyRatesHolder currencyRatesHolder,
                                 final CurrencyRepository currencyRepository) {
        this.currencyRatesHolder = currencyRatesHolder;
        this.currencyRepository = currencyRepository;
    }


    @Override
    public void validate(final Currency currency) {
        currency.setName(currency.getName().toUpperCase());
        if (!currencyRatesHolder.contains(currency)) {
            if (!currencyRepository.exists(Example.of(currency))) {
                throw new DataNotFoundException("Currency does not exist.", CURRENCY_NOT_FOUND);
            }
        }
    }
}
