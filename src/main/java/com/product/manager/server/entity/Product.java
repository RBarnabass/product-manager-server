package com.product.manager.server.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Product {

    @Id
    private Long id;
}
