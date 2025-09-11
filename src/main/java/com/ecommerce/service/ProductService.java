package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getAllProducts(Integer PAGE_NUMBER, Integer PAGE_SIZE, String SORT_PRODUCTS_BY, String SORT_DIR);

    ProductResponse searchByCategory(Long categoryId, Integer PAGE_NUMBER, Integer PAGE_SIZE, String SORT_PRODUCTS_BY, String SORT_DIR);

    ProductResponse searchProductByKeyword(String keyword, Integer PAGE_NUMBER, Integer PAGE_SIZE, String SORT_PRODUCTS_BY, String SORT_DIR);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);
}
