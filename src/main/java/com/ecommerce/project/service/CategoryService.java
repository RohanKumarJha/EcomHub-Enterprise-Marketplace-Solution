package com.ecommerce.project.service;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer PAGE_NUMBER,Integer PAGE_SIZE,String SORT_CATEGORIES_BY,String SORT_DIR);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long categoryId,CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
}
