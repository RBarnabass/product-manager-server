package com.product.manager.server.service.currency;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyProvider {

    Map<String, BigDecimal> getCurrencyRates();
}
