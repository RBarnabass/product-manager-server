package com.product.manager.server.controller;

import com.product.manager.server.converter.ProductConverter;
import com.product.manager.server.dto.category.CategoryDTO;
import com.product.manager.server.dto.product.CreateProductDTO;
import com.product.manager.server.dto.product.ProductDTO;
import com.product.manager.server.entity.Product;
import com.product.manager.server.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/product/manager")
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @Autowired
    public ProductController(final ProductService productService, final ProductConverter productConverter) {
        this.productService = productService;
        this.productConverter = productConverter;
    }


    @PostMapping("/products")
    @ResponseStatus(CREATED)
    public ProductDTO add(@Validated @RequestBody final CreateProductDTO createProductDTO) {
        final Product product = productConverter.convertToEntity(createProductDTO);
        final Product savedProduct = productService.add(product);
        return productConverter.convertToDto(savedProduct);
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(OK)
    public ProductDTO get(@PathVariable(value = "id") final Long id) {
        final Product product = productService.get(id);
        return productConverter.convertToDto(product);
    }

    @GetMapping("/products")
    @ResponseStatus(OK)
    public List<ProductDTO> getAll() {
        final List<Product> products = productService.getAll();
        return productConverter.convertToDto(products);
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(OK)
    public ProductDTO update(@Validated @RequestBody final CreateProductDTO createProductDTO,
                              @PathVariable("id") final Long id) {
        final Product product = productConverter.convertToEntity(createProductDTO);
        final Product updateProduct = productService.update(product, id);
        return productConverter.convertToDto(updateProduct);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable("id") final Long id) {
        productService.delete(id);
    }
}