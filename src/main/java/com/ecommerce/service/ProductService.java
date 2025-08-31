package com.ecommerce.service;

import com.ecommerce.dto.APISuccessResponse;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponse;

public interface ProductService {
    APISuccessResponse createProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse findProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    APISuccessResponse updateProduct(Long productId, ProductDTO productDTO);

    APISuccessResponse deleteProduct(Long productId);
}
