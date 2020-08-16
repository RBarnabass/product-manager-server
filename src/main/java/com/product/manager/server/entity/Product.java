package com.product.manager.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static com.product.manager.server.constant.DigitConstant.MAX_PRODUCT_MODEL_LENGTH;
import static com.product.manager.server.constant.DigitConstant.MAX_PRODUCT_NAME_LENGTH;
import static com.product.manager.server.constant.Table.PRODUCTS;
import static javax.persistence.CascadeType.ALL;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = PRODUCTS)
public class Product extends BaseEntity {

    @Column(nullable = false, length = MAX_PRODUCT_NAME_LENGTH)
    private String name;

    @Column(unique = true, nullable = false, length = MAX_PRODUCT_MODEL_LENGTH)
    private String model;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private Currency currency;

    @OneToOne(cascade = ALL)
    private CurrencyExchangeInfo currencyExchangeInfo;

    @ManyToMany
    private List<Category> categories;
}
