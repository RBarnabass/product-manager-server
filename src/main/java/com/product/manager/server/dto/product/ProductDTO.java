package com.product.manager.server.dto.product;

import com.product.manager.server.entity.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String model;
    private Integer quantity;
    private BigDecimal price;
    private Currency currency;
    private List<String> categories;
}
