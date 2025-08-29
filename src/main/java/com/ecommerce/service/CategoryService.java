package com.ecommerce.service;

import com.ecommerce.dto.APISuccessResponse;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponse;
import jakarta.validation.Valid;

public interface CategoryService {
    CategoryResponse getAllCategories();

    APISuccessResponse saveCategory(@Valid CategoryDTO categoryDTO);

    APISuccessResponse updateCategory(Long categoryId, @Valid CategoryDTO categoryDTO);

    APISuccessResponse deleteCategory(Long categoryId);
}
