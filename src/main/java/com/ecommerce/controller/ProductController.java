package com.ecommerce.controller;

import com.ecommerce.config.PageConstant;
import com.ecommerce.dto.APISuccessResponse;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<APISuccessResponse> createProduct(@PathVariable Long categoryId, @Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(categoryId, productDTO), HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = PageConstant.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.SORT_PRODUCT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = PageConstant.SORT_ORDER) String sortOrder) {
        return new ResponseEntity<>(productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> findProductsByCategory(@PathVariable Long categoryId,
                                                                  @RequestParam(value = "pageNumber", defaultValue = PageConstant.PAGE_NUMBER) Integer pageNumber,
                                                                  @RequestParam(value = "pageSize", defaultValue = PageConstant.PAGE_SIZE) Integer pageSize,
                                                                  @RequestParam(value = "sortBy", defaultValue = PageConstant.SORT_PRODUCT_BY) String sortBy,
                                                                  @RequestParam(value = "sortOrder", defaultValue = PageConstant.SORT_ORDER) String sortOrder) {
        return new ResponseEntity<>(productService.findProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder),HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable("keyword") String keyword,
                                                               @RequestParam(value = "pageNumber", defaultValue = PageConstant.PAGE_NUMBER) Integer pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = PageConstant.PAGE_SIZE) Integer pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = PageConstant.SORT_PRODUCT_BY) String sortBy,
                                                               @RequestParam(value = "sortOrder", defaultValue = PageConstant.SORT_ORDER) String sortOrder) {
        return new ResponseEntity<>(productService.getProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<APISuccessResponse> updateProduct(@PathVariable Long productId,@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(productId, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<APISuccessResponse> deleteProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }
}
