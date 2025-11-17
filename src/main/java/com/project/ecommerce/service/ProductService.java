package com.project.ecommerce.service;

import com.project.ecommerce.payload.ProductDTO;
import com.project.ecommerce.payload.ProductResponse;

public interface ProductService {
    ProductDTO createProduct(Long categoryId, ProductDTO productDTO);
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    ProductResponse getProductByCategory(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    ProductResponse getProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    ProductDTO deleteProduct(Long productId);
}
