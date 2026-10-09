package com.millie.financemanager.service;

import com.millie.financemanager.dto.CategoryRequestDto;
import com.millie.financemanager.entity.Category;
import com.millie.financemanager.exception.NotFoundException;
import com.millie.financemanager.repository.CategoryRepository;
import com.millie.financemanager.repository.ExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void shouldReturnAllCategories() {

        Category category = new Category();
        category.setId(1L);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.getCategories();

        Assertions.assertEquals(1, result.toArray().length);
        Assertions.assertEquals(category, result.getFirst());

    }

    @Test
    void shouldReturnCategoryById() {

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategory(1L);

        Assertions.assertEquals(category, result);
    }

    @Test
    void shouldThrowExceptionIfCategoryIsNotFound() {

        Assertions.assertThrows(NotFoundException.class, () -> categoryService.getCategory(1L));

    }

    @Test
    void shouldReturnCreatedCategory() {

        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Nova Categoria");

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category result = categoryService.createCategory(requestDto);

        Assertions.assertEquals(requestDto.getName(), result.getName());
    }

    @Test
    void shouldDeleteCategory() {

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategoryById(1L);

        Mockito.verify(expenseRepository).findExpenseByCategory(any(Category.class));
        Mockito.verify(categoryRepository).delete(category);

    }
}
