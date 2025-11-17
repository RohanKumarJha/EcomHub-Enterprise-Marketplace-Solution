package com.project.ecommerce.repository;

import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);

    Optional<Product> findByProductName(@NotBlank(message = "ProductName should not blank") @Size(min = 5, max = 20, message = "ProductName should between 5 and 20") String productName);
}
