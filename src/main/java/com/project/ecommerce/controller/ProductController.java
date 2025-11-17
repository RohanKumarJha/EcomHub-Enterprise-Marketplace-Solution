package com.project.ecommerce.controller;

import com.project.ecommerce.config.AppConstants;
import com.project.ecommerce.payload.ProductDTO;
import com.project.ecommerce.payload.ProductResponse;
import com.project.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> createProduct(@PathVariable Long categoryId, @Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(categoryId,productDTO), HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "PAGE_NUMBER", defaultValue = AppConstants.PAGE_NUMBER) Integer PAGE_NUMBER,
            @RequestParam(name = "PAGE_SIZE", defaultValue = AppConstants.PAGE_SIZE) Integer PAGE_SIZE,
            @RequestParam(name = "PRODUCTS_SORT_BY", defaultValue = AppConstants.PRODUCTS_SORT_BY) String PRODUCTS_SORT_BY,
            @RequestParam(name = "SORT_DIR", defaultValue = AppConstants.SORT_DIR) String SORT_DIR) {
        return new ResponseEntity<>(productService.getAllProducts(PAGE_NUMBER,PAGE_SIZE,PRODUCTS_SORT_BY,SORT_DIR),HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId,
            @RequestParam(name = "PAGE_NUMBER", defaultValue = AppConstants.PAGE_NUMBER) Integer PAGE_NUMBER,
            @RequestParam(name = "PAGE_SIZE", defaultValue = AppConstants.PAGE_SIZE) Integer PAGE_SIZE,
            @RequestParam(name = "PRODUCTS_SORT_BY", defaultValue = AppConstants.PRODUCTS_SORT_BY) String PRODUCTS_SORT_BY,
            @RequestParam(name = "SORT_DIR", defaultValue = AppConstants.SORT_DIR) String SORT_DIR) {
        return new ResponseEntity<>(productService.getProductByCategory(categoryId,PAGE_NUMBER,PAGE_SIZE,PRODUCTS_SORT_BY,SORT_DIR),HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword,
           @RequestParam(name = "PAGE_NUMBER", defaultValue = AppConstants.PAGE_NUMBER) Integer PAGE_NUMBER,
           @RequestParam(name = "PAGE_SIZE", defaultValue = AppConstants.PAGE_SIZE) Integer PAGE_SIZE,
           @RequestParam(name = "PRODUCTS_SORT_BY", defaultValue = AppConstants.PRODUCTS_SORT_BY) String PRODUCTS_SORT_BY,
           @RequestParam(name = "SORT_DIR", defaultValue = AppConstants.SORT_DIR) String SORT_DIR) {
        return new ResponseEntity<>(productService.getProductByKeyword(keyword,PAGE_NUMBER,PAGE_SIZE,PRODUCTS_SORT_BY,SORT_DIR),HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(productId,productDTO),HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId),HttpStatus.OK);
    }
}
