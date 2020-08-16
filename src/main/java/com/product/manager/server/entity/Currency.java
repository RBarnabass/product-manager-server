package com.product.manager.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.product.manager.server.constant.DigitConstant.MAX_CURRENCY_NAME_LENGTH;
import static com.product.manager.server.constant.Table.CURRENCIES;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CURRENCIES)
public class Currency extends BaseEntity {

    @Column(unique = true, nullable = false, length = MAX_CURRENCY_NAME_LENGTH)
    private String name;
}
