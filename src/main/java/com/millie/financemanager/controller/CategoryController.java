package com.millie.financemanager.controller;

import com.millie.financemanager.entity.Category;
import com.millie.financemanager.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {

        ArrayList<Category> categoriesList = (ArrayList<Category>) categoryService.getCategories();

        return new ResponseEntity<List<Category>> (categoriesList, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable long categoryId) {
        return new ResponseEntity<Category>(categoryService.getCategory(categoryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return new ResponseEntity<Category>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<String>("Category has been deleted successfully", HttpStatus.OK);
    }


}
