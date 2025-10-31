package com.project.ecommerce.service;

import com.project.ecommerce.exception.APIException;
import com.project.ecommerce.exception.ResourceAlreadyExist;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.payload.CategoryDTO;
import com.project.ecommerce.payload.CategoryResponse;
import com.project.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer PAGE_NUMBER, Integer PAGE_SIZE, String CATEGORIES_SORT_BY, String SORT_DIR) {
        Sort sort = SORT_DIR.equalsIgnoreCase("asc") // Apply sorting
                ? Sort.by(CATEGORIES_SORT_BY).ascending()
                : Sort.by(CATEGORIES_SORT_BY).descending();

        Pageable page = PageRequest.of(PAGE_NUMBER,PAGE_SIZE,sort); // Apply pagination
        Page<Category> categories = categoryRepository.findAll(page);
        List<Category> categoryList = categories.getContent();

        if(categoryList.isEmpty()) {
            throw new APIException("Category list is empty");
        }

        List<CategoryDTO> categoryDTOS = categoryList.stream().map(category ->
                modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryDTOS(categoryDTOS);
        categoryResponse.setPage_number(categories.getNumber());
        categoryResponse.setPage_size(categories.getSize());
        categoryResponse.setTotal_elements(categories.getTotalElements());
        categoryResponse.setTotal_pages(categories.getTotalPages());
        categoryResponse.setLast_page(categories.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.findByCategoryName(categoryDTO.getCategoryName()).isPresent()) {
            throw new ResourceAlreadyExist("Category already exists with name: ", categoryDTO.getCategoryName());
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }


    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Optional<Category> existing = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

        if (existing.isPresent() && !existing.get().getCategoryId().equals(categoryId)) {
            throw new ResourceAlreadyExist("Category already exists with name: ", categoryDTO.getCategoryName());
        }

        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }




    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        // Step 1: Find category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        // Step 2: Delete category
        categoryRepository.delete(category);

        // Step 3: Return deleted category details (optional, for confirmation)
        return modelMapper.map(category, CategoryDTO.class);
    }

}
