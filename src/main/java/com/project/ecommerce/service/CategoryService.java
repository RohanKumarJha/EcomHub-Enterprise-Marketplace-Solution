package com.project.ecommerce.service;

import com.project.ecommerce.payload.CategoryDTO;
import com.project.ecommerce.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer PAGE_NUMBER, Integer PAGE_SIZE, String CATEGORIES_SORT_BY, String SORT_DIR);
    CategoryDTO createCategory(CategoryDTO categoryDto);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDto);
    CategoryDTO deleteCategory(Long categoryId);
}
