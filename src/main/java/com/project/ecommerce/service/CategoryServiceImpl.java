package com.project.ecommerce.service;

import com.project.ecommerce.exception.EmptyListException;
import com.project.ecommerce.exception.ResourceAlreadyExistException;
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

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse getAllCategories(Integer PAGE_NUMBER, Integer PAGE_SIZE, String CATEGORIES_SORT_BY, String SORT_DIR) {
        Sort sort = SORT_DIR.equalsIgnoreCase("asc")
                ? Sort.by(CATEGORIES_SORT_BY).ascending()
                : Sort.by(CATEGORIES_SORT_BY).descending();
        Pageable page = PageRequest.of(PAGE_NUMBER,PAGE_SIZE,sort);
        Page<Category> categoryPage = categoryRepository.findAll(page);
        List<Category> categoryList = categoryPage.getContent();
        if(categoryList.isEmpty()) {
            throw new EmptyListException("No category exist");
        }
        List<CategoryDTO> response = categoryList.stream().map(category ->
                modelMapper.map(category, CategoryDTO.class)).toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryDtoList(response);
        categoryResponse.setPage_number(categoryPage.getNumber());
        categoryResponse.setPage_size(categoryPage.getSize());
        categoryResponse.setTotal_elements(categoryPage.getTotalElements());
        categoryResponse.setTotal_pages(categoryPage.getTotalPages());
        categoryResponse.setLast_page(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.findByCategoryName(categoryDTO.getCategoryName()).isPresent()) {
            throw new ResourceAlreadyExistException("Category already exists with name: ", categoryDTO.getCategoryName());
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exist with ", "id ", categoryId));
        if (categoryRepository.findByCategoryName(categoryDTO.getCategoryName()).isPresent()) {
            throw new ResourceAlreadyExistException("Category already exists with name: ", categoryDTO.getCategoryName());
        }
        Optional<Category> existing = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

        if (existing.isPresent() && !existing.get().getCategoryId().equals(categoryId)) {
            throw new ResourceAlreadyExistException("Category already exists with name: ", categoryDTO.getCategoryName());
        }

        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exists with name: ", "id ", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }
}
