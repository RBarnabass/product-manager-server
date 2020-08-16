package com.product.manager.server.repository;

import com.product.manager.server.entity.Currency;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends ApplicationRepository<Currency, Long> {

    Optional<Currency> findByName(final String name);
}
