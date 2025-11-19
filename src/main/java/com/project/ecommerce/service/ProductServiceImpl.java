package com.project.ecommerce.service;

import com.project.ecommerce.exception.EmptyListException;
import com.project.ecommerce.exception.ResourceAlreadyExistException;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.payload.ProductDTO;
import com.project.ecommerce.payload.ProductResponse;
import com.project.ecommerce.repository.CategoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO createProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setImage("default.png");
        if (product.getDiscount() > 0) {
            double discountAmount = (product.getDiscount() / 100) * product.getPrice();
            product.setSpecialPrice(product.getPrice() - discountAmount);
        } else {
            product.setSpecialPrice(product.getPrice());
        }
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }


    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy,
                                          String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> pageProducts = productRepository.findAll(pageable);
        List<Product> products = pageProducts.getContent();
        if (products.isEmpty()) {
            throw new EmptyListException("No products found");
        }
        List<ProductDTO> productDTOList = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOList(productDTOList);
        productResponse.setPage_number(pageProducts.getNumber());
        productResponse.setPage_size(pageProducts.getSize());
        productResponse.setTotal_elements(pageProducts.getTotalElements());
        productResponse.setTotal_pages(pageProducts.getTotalPages());
        productResponse.setLast_page(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductByCategory(Long categoryId, Integer pageNumber, Integer pageSize,
                                                String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> pageProducts = productRepository.findByCategory(category, pageable);
        List<Product> products = pageProducts.getContent();
        if (products.isEmpty()) {
            throw new EmptyListException("No products found in category: " + category.getCategoryName());
        }
        List<ProductDTO> productDTOList = products.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOList(productDTOList);
        productResponse.setPage_number(pageProducts.getNumber());
        productResponse.setPage_size(pageProducts.getSize());
        productResponse.setTotal_elements(pageProducts.getTotalElements());
        productResponse.setTotal_pages(pageProducts.getTotalPages());
        productResponse.setLast_page(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword, Integer pageNumber, Integer pageSize,
                                               String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> pageProducts = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
        List<Product> products = pageProducts.getContent();
        if (products.isEmpty()) {
            throw new EmptyListException("No products found for keyword: " + keyword);
        }
        List<ProductDTO> productDTOList = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDTOList(productDTOList);
        productResponse.setPage_number(pageProducts.getNumber());
        productResponse.setPage_size(pageProducts.getSize());
        productResponse.setTotal_elements(pageProducts.getTotalElements());
        productResponse.setTotal_pages(pageProducts.getTotalPages());
        productResponse.setLast_page(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        Optional<Product> duplicateProduct = productRepository.findByProductName(productDTO.getProductName());
        if (duplicateProduct.isPresent() && !duplicateProduct.get().getProductId().equals(productId)) {
            throw new ResourceAlreadyExistException("Product already exist with name ", productDTO.getProductName());
        }
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDiscount(productDTO.getDiscount());
        existingProduct.setQuantity(productDTO.getQuantity());
        if (existingProduct.getDiscount() > 0) {
            double discountAmount = (existingProduct.getDiscount() / 100) * existingProduct.getPrice();
            existingProduct.setSpecialPrice(existingProduct.getPrice() - discountAmount);
        } else {
            existingProduct.setSpecialPrice(existingProduct.getPrice());
        }
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(existingProduct);
        return modelMapper.map(existingProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        String fileName = fileService.uploadImage(path, image);
        existingProduct.setImage(fileName);
        Product savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }


}
