package com.ecommerce.service;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer PAGE_NUMBER, Integer PAGE_SIZE, String SORT_CATEGORIES_BY, String SORT_DIR);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
