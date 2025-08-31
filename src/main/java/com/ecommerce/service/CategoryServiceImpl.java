package com.ecommerce.service;

import com.ecommerce.dto.APISuccessResponse;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.exceptions.APIException;
import com.ecommerce.exceptions.ResourceAlreadyExistException;
import com.ecommerce.exceptions.ResourceNotExistException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageDetails);
        List<Category> categoryList = page.getContent();
        if(categoryList.isEmpty()) {
            throw new APIException("Category page","is empty");
        }
        List<CategoryDTO> categoryDTOS = categoryList.stream().map(category ->
            modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setResponse(categoryDTOS);
        categoryResponse.setPageNumber(page.getNumber());
        categoryResponse.setPageSize(page.getSize());
        categoryResponse.setNoOfElements(page.getNumberOfElements());
        categoryResponse.setLastPage(page.isLast());
        return categoryResponse;
    }

    @Override
    public APISuccessResponse saveCategory(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new ResourceAlreadyExistException("Category","already exist with","categoryName",categoryDTO.getCategoryName());
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO categoryDTOs = modelMapper.map(savedCategory, CategoryDTO.class);
        return new APISuccessResponse("Category saved successfully",true, categoryDTOs);
    }

    @Override
    public APISuccessResponse updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category findCategory = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotExistException("Category","not present with","categoryId",categoryId));
        findCategory.setCategoryName(categoryDTO.getCategoryName());
        Category savedCategory = categoryRepository.save(findCategory);
        CategoryDTO categoryDTOs = modelMapper.map(savedCategory, CategoryDTO.class);
        return new APISuccessResponse("Category updated successfully",true, categoryDTOs);
    }

    @Override
    public APISuccessResponse deleteCategory(Long categoryId) {
        Category findCategory = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotExistException("Category","not present with","categoryId",categoryId));
        categoryRepository.delete(findCategory);
        CategoryDTO categoryDTOs = modelMapper.map(findCategory, CategoryDTO.class);
        return new APISuccessResponse("Category deleted successfully",true, categoryDTOs);
    }
}
