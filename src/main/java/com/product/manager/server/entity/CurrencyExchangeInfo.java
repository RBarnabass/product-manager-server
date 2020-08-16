package com.product.manager.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

import static com.product.manager.server.constant.Table.CURRENCY_EXCHANGE_INFO;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CURRENCY_EXCHANGE_INFO)
public class CurrencyExchangeInfo extends BaseEntity {

    @ManyToOne
    private Currency currency;

    @Column(nullable = false)
    private BigDecimal rate;
}
