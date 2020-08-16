package com.product.manager.server.converter;

import com.product.manager.server.dto.category.CategoryDTO;
import com.product.manager.server.dto.category.CreateCategoryDTO;
import com.product.manager.server.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryConverter extends Converter<Category, CategoryDTO> {

    CategoryDTO convertToDto(final Category category);
    Category convertToEntity(final CreateCategoryDTO createCategoryDTO);
}
