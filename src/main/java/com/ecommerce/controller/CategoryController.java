package com.ecommerce.controller;

import com.ecommerce.config.PageConstant;
import com.ecommerce.dto.APISuccessResponse;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.service.CategoryService;
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
            @RequestParam(value = "pageNumber", defaultValue = PageConstant.pageNumber) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.sortBy) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = PageConstant.sortOrder) String sortOrder) {
        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<APISuccessResponse> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.saveCategory(categoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<APISuccessResponse> updateCategory(@PathVariable Long categoryId,@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, categoryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<APISuccessResponse> deleteCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.deleteCategory(categoryId), HttpStatus.OK);
    }
}
