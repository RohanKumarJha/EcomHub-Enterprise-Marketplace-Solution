package com.project.ecommerce.controller;

import com.project.ecommerce.config.AppConstants;
import com.project.ecommerce.payload.CategoryDTO;
import com.project.ecommerce.payload.CategoryResponse;
import com.project.ecommerce.service.CategoryService;
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
            @RequestParam(name = "CATEGORIES_SORT_BY", defaultValue = AppConstants.CATEGORIES_SORT_BY) String CATEGORIES_SORT_BY,
            @RequestParam(name = "SORT_DIR", defaultValue = AppConstants.SORT_DIR) String SORT_DIR) {
        return new ResponseEntity<>(categoryService.getAllCategories(PAGE_NUMBER,PAGE_SIZE,CATEGORIES_SORT_BY,SORT_DIR), HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto),HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO categoryDto) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId,categoryDto),HttpStatus.OK);
    }

    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.deleteCategory(categoryId),HttpStatus.OK);
    }
}
