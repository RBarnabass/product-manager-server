package com.product.manager.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.product.manager.server.converter.CategoryConverter;
import com.product.manager.server.dto.category.CategoryDTO;
import com.product.manager.server.dto.category.CreateCategoryDTO;
import com.product.manager.server.entity.Category;
import com.product.manager.server.service.category.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class TestController {

    private static final String PATH = "/api/v1/product/manager/categories";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryConverter categoryConverter;

    @BeforeEach
    public void setUp() {
        reset(categoryService, categoryConverter);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(categoryService, categoryConverter);
    }

    @Test
    public void r3() throws Exception {
        final CreateCategoryDTO createCategoryDTO = new CreateCategoryDTO();
        final Category category = new Category();
        final CategoryDTO categoryDTO = new CategoryDTO();

        given(categoryConverter.convertToEntity(any(CreateCategoryDTO.class))).willReturn(category);
        given(categoryService.add(category)).willReturn(category);
        given(categoryConverter.convertToDto(category)).willReturn(categoryDTO);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCategoryDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(categoryConverter).convertToEntity(any(CreateCategoryDTO.class));
        verify(categoryService).add(category);
        verify(categoryConverter).convertToDto(category);
    }

    @Test
    public void r() throws Exception {
        mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void r1() throws Exception {
        mockMvc.perform(get(PATH + "/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryService).get(anyLong());
    }
}
