package com.product.manager.server.service.currency.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.manager.server.exception.CurrencyApiException;
import com.product.manager.server.service.currency.CurrencyProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.product.manager.server.exception.api.ExceptionCode.CURRENCY_UPDATE_FAILURE;

@Slf4j
@Service
public class CurrencyProviderImpl implements CurrencyProvider {

    private static final String CURRENCY_RATES_FIELD_NAME = "rates";
    private static final String URL = "http://data.fixer.io/api/latest?access_key=";
    private static final String API_KEY = "2253a034050df811fc3736a38726ce2b";

    private final ObjectMapper objectMapper;

    @Autowired
    public CurrencyProviderImpl(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public Map<String, BigDecimal> getCurrencyRates() {
        try {
            final JsonNode jsonNode = objectMapper.readTree(new URL(URL + API_KEY));
            final JsonNode rates = jsonNode.get(CURRENCY_RATES_FIELD_NAME);
            return parseCurrencyNodeAndPutToMap(rates);
        } catch (IOException e) {
            log.error("Exception during currency rates parsing process: {}", e.getMessage(), e);
            throw new CurrencyApiException("Currency update exception.", CURRENCY_UPDATE_FAILURE);
        }
    }

    private Map<String, BigDecimal> parseCurrencyNodeAndPutToMap(final JsonNode jsonNode) {
        final Iterator<String> stringIterator = jsonNode.fieldNames();
        final Map<String, BigDecimal> currencyRates = new HashMap<>();
        JsonNode rateNode;
        String currentCurrencyNode;

        while (stringIterator.hasNext()) {
            currentCurrencyNode = stringIterator.next();
            rateNode = jsonNode.get(stringIterator.next());
            currencyRates.put(currentCurrencyNode, rateNode.decimalValue());
        }
        return currencyRates;
    }
}
