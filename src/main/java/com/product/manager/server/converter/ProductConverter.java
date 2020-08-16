package com.product.manager.server.converter;

import com.product.manager.server.dto.product.CreateProductDTO;
import com.product.manager.server.dto.product.ProductDTO;
import com.product.manager.server.entity.Category;
import com.product.manager.server.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductConverter extends Converter<Product, ProductDTO> {

    ProductDTO convertToDto(final Product product);

    @Mapping(target = "currency.name", source = "createProductDTO.currencyName")
    Product convertToEntity(final CreateProductDTO createProductDTO);

    default List<Category> map(final List<String> categories) {
        return categories.stream()
                .map(categoryName -> {
                    final Category category = new Category();
                    category.setName(categoryName.trim());
                    return category;
                }).collect(Collectors.toList());
    }

    default List<String> mapToString(final List<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
}
