package com.ecommerce.repository;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductName(String productName);

    Page<Product> findByCategory(Category findCategory, Pageable pageDetails);

    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageDetails);
}
