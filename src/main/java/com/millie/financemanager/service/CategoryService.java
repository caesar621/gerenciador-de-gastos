package com.millie.financemanager.service;

import com.millie.financemanager.dto.CategoryRequestDto;
import com.millie.financemanager.entity.Category;
import com.millie.financemanager.entity.Expense;
import com.millie.financemanager.exception.NotFoundException;
import com.millie.financemanager.repository.CategoryRepository;
import com.millie.financemanager.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    public CategoryService(CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(Category.class, categoryId));
    }

    public Category createCategory(CategoryRequestDto category) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setColor(category.getColor());
        return categoryRepository.save(newCategory);
    }

    @Transactional
    public void deleteCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        List<Expense> expensesList = expenseRepository.getExpenseByCategory(category);

        expensesList.forEach(expense -> expense.setCategory(null));
        expenseRepository.saveAll(expensesList);

        categoryRepository.delete(category);
    }
}
