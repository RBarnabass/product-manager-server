package com.product.manager.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.product.manager.server.converter.CategoryConverter;
import com.product.manager.server.dto.category.CategoryDTO;
import com.product.manager.server.dto.category.CreateCategoryDTO;
import com.product.manager.server.entity.Category;
import com.product.manager.server.exception.DataNotFoundException;
import com.product.manager.server.service.category.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CategoryControllerTest {

    private static final String PATH = "/api/v1/product/manager/categories";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryConverter categoryConverter;

    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "admin";

    private Category category;
    private CategoryDTO categoryDTO;
    private CreateCategoryDTO createCategoryDTO;

    @BeforeEach
    public void setUp() {
        createCategoryDTO = new CreateCategoryDTO();
        createCategoryDTO.setName("Sport");
        category = new Category();
        categoryDTO = new CategoryDTO();
        categoryDTO.setName(createCategoryDTO.getName());

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        reset(categoryConverter, categoryService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(categoryService, categoryConverter);
    }

    @Test
    public void authenticatedFailTest() throws Exception {
        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCategoryDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username=USERNAME, password = PASSWORD)
    public void successfullyCreateCategoryTest() throws Exception {
        given(categoryConverter.convertToEntity(any(CreateCategoryDTO.class))).willReturn(category);
        given(categoryService.add(category)).willReturn(category);
        given(categoryConverter.convertToDto(category)).willReturn(categoryDTO);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCategoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(createCategoryDTO.getName())));

        verify(categoryConverter).convertToEntity(any(CreateCategoryDTO.class));
        verify(categoryService).add(category);
        verify(categoryConverter).convertToDto(category);
    }

    @Test
    @WithMockUser(username=USERNAME, password = PASSWORD)
    public void getAllCategoriesTest() throws Exception {
        final List<Category> categories = List.of(category);
        given(categoryService.getAll()).willReturn(categories);
        given(categoryConverter.convertToDto(categories)).willReturn(List.of(categoryDTO));

        mockMvc.perform(get(PATH))
                .andExpect(status().isOk());

        verify(categoryService).getAll();
        verify(categoryConverter).convertToDto(categories);
    }


    @Test
    @WithMockUser(username=USERNAME, password = PASSWORD)
    public void getNotExistedCategoryTest() throws Exception {
        given(categoryService.get(1L)).willThrow(DataNotFoundException.class);

        mockMvc.perform(get(PATH + "/1"))
                .andExpect(status().isNotFound());

        verify(categoryService).get(anyLong());
    }
}
