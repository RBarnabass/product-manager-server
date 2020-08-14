package com.product.manager.server.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Category {

    @Id
    private Long id;
}
