package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "PAGE_NUMBER", defaultValue = AppConstants.PAGE_NUMBER) Integer PAGE_NUMBER,
            @RequestParam(name = "PAGE_SIZE", defaultValue = AppConstants.PAGE_SIZE) Integer PAGE_SIZE,
            @RequestParam(name = "SORT_CATEGORIES_BY", defaultValue = AppConstants.SORT_CATEGORIES_BY) String SORT_CATEGORIES_BY,
            @RequestParam(name = "SORT_DIR", defaultValue = AppConstants.SORT_DIR) String SORT_DIR) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(PAGE_NUMBER,PAGE_SIZE,SORT_CATEGORIES_BY,SORT_DIR);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO),HttpStatus.CREATED);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updatedCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId,categoryDTO),HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.deleteCategory(categoryId),HttpStatus.OK);
    }
}
