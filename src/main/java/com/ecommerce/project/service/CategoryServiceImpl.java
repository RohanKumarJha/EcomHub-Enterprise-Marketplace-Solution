package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceAlreadyExistsException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer PAGE_NUMBER,Integer PAGE_SIZE,String SORT_CATEGORIES_BY,String SORT_DIR) {

        //  🔹 Sorting
        Sort sort = SORT_DIR.equalsIgnoreCase("asc")
                ? Sort.by(SORT_CATEGORIES_BY).ascending()
                : Sort.by(SORT_CATEGORIES_BY).descending();

        //  🔹 Pagination
        Pageable pageable = PageRequest.of(PAGE_NUMBER,PAGE_SIZE,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        List<Category> categoryList = page.getContent();
        if(categoryList.isEmpty()) {
            throw new APIException("Category");
        }
        List<CategoryDTO> categoryDTOS = categoryList.stream().map(category ->
                        modelMapper.map(category, CategoryDTO.class)).toList();

        // 🔹 Build CategoryResponse object
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(page.getNumber());
        categoryResponse.setPageSize(page.getSize());
        categoryResponse.setTotalElements(page.getTotalElements());
        categoryResponse.setTotalPages(page.getTotalPages());
        categoryResponse.setLastPage(page.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new ResourceAlreadyExistsException("Category", "categoryName", categoryDTO.getCategoryName());
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));

        // Check for duplicate name (if updating to a name that already exists)
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName()) &&
                !category.getCategoryName().equalsIgnoreCase(categoryDTO.getCategoryName())) {
            throw new ResourceAlreadyExistsException("Category", "categoryName", categoryDTO.getCategoryName());
        }

        category.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }


    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }


}
