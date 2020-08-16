package com.product.manager.server.service.currency.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.manager.server.dto.currency.CurrencyApiResponseDTO;
import com.product.manager.server.exception.CurrencyApiResponseParsingException;
import com.product.manager.server.service.currency.CurrencyProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static com.product.manager.server.exception.api.ExceptionCode.CURRENCY_UPDATE_FAILURE;

@Slf4j
@Service
public class CurrencyProviderImpl implements CurrencyProvider {

    private static final String URL = "http://data.fixer.io/api/latest?access_key=";
    private static final String API_KEY = "2253a034050df811fc3736a38726ce2b";

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyProviderImpl(final ObjectMapper objectMapper, final RestTemplateBuilder restTemplateBuilder) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public Map<String, BigDecimal> getCurrencyRates() {
            final ResponseEntity<String> forEntity = restTemplate.getForEntity(URL + API_KEY, String.class);
        try {
            final CurrencyApiResponseDTO currencyApiResponseDTO =
                    objectMapper.readValue(forEntity.getBody(), CurrencyApiResponseDTO.class);
            return currencyApiResponseDTO.getRates();
        } catch (JsonProcessingException e) {
            log.error("Exception during currency rates parsing process: {}", e.getMessage(), e);
            throw new CurrencyApiResponseParsingException("Currency update exception.", CURRENCY_UPDATE_FAILURE);
        }
    }
}
