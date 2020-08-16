package com.product.manager.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;

import java.util.List;

import static com.product.manager.server.constant.DigitConstant.MAX_CATEGORY_NAME_LENGTH;
import static com.product.manager.server.constant.Table.CATEGORIES;
import static org.hibernate.annotations.LazyCollectionOption.EXTRA;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = CATEGORIES)
public class Category extends BaseEntity {

    @Column(unique = true, nullable = false, length = MAX_CATEGORY_NAME_LENGTH)
    private String name;

    @LazyCollection(EXTRA)
    @ManyToMany(mappedBy = CATEGORIES)
    private List<Product> products;
}
