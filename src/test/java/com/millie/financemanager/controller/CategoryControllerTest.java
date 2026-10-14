package com.millie.financemanager.controller;

import com.millie.financemanager.appConfig;
import com.millie.financemanager.dto.CategoryRequestDto;
import com.millie.financemanager.entity.Category;
import com.millie.financemanager.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(appConfig.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    void shouldReturnCategoryById() throws Exception {

        Category category = new Category();
        category.setId(1L);
        category.setName("Transporte");

        when(categoryService.getCategory(1L)).thenReturn(category);

        mockMvc.perform(get("/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Transporte"));

    }

    @Test
    void shouldReturnAllCategories() throws Exception {

        Category category = new Category();
        category.setId(1L);
        category.setName("Transporte");

        List<Category> categoryList= new ArrayList<>();
        categoryList.add(category);

        when(categoryService.getCategories()).thenReturn(categoryList);

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Transporte"));

    }

    @Test
    void shouldReturnSuccessfullyCreatedCategory() throws Exception {

        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Transporte");
        requestDto.setColor("Azul");

        Category category = new Category();
        category.setId(1L);
        category.setName("Transporte");
        category.setColor("Azul");

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        when(categoryService.createCategory(any(CategoryRequestDto.class))).thenReturn(category);

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

    }

    @Test
    void shouldSuccessfullyDeleteCategory() throws Exception {

        mockMvc.perform(delete("/category/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(categoryService).deleteCategoryById(1L);
    }
}
