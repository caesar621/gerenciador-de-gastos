package com.millie.financemanager.controller;

import com.millie.financemanager.dto.CategoryRequestDto;
import com.millie.financemanager.dto.CategoryResponseDto;
import com.millie.financemanager.entity.Category;
import com.millie.financemanager.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {

        List<CategoryResponseDto> categoriesList = categoryService.getCategories().stream().map(category -> convertToDto(category)).toList();

        return ResponseEntity.status(HttpStatus.OK).body(categoriesList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable long categoryId) {
        CategoryResponseDto category = convertToDto(categoryService.getCategory(categoryId));
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryRequestDto category) {
        CategoryResponseDto newCategory = convertToDto(categoryService.createCategory(category));
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private CategoryResponseDto convertToDto(Category category) {
        return modelMapper.map(category, CategoryResponseDto.class);
    }


}
