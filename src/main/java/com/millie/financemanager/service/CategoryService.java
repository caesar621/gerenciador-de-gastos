package com.millie.financemanager.service;

import com.millie.financemanager.dto.CategoryRequestDto;
import com.millie.financemanager.entity.Category;
import com.millie.financemanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    public Category createCategory(CategoryRequestDto category) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setColor(category.getColor());
        return categoryRepository.save(newCategory);
    }

    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
