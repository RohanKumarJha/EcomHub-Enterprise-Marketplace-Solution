package com.ecommerce.service;

import com.ecommerce.dto.APISuccessResponse;
import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.exceptions.APIException;
import com.ecommerce.exceptions.ResourceAlreadyExistException;
import com.ecommerce.exceptions.ResourceNotExistException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public APISuccessResponse createProduct(Long categoryId, ProductDTO productDTO) {
        Category findCategory = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotExistException("Category", "not present with", "categoryId", categoryId));
        Product existingProduct = productRepository.findByProductName(productDTO.getProductName());
        if (existingProduct != null) {
            throw new ResourceAlreadyExistException("Product","already exist with","productName",productDTO.getProductName());
        }
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(findCategory);
        Product savedProduct = productRepository.save(product);
        return new APISuccessResponse("Product created successfully", true,modelMapper.map(savedProduct, ProductDTO.class));
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageDetails);
        List<Product> productList = page.getContent();
        if(productList.isEmpty()) {
            throw new APIException("Product page","is empty");
        }
        List<ProductDTO> productDTOList = productList.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setResponse(productDTOList);
        productResponse.setPageNumber(page.getNumber());
        productResponse.setPageSize(page.getSize());
        productResponse.setNoOfElements(page.getNumberOfElements());
        productResponse.setLastPage(page.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse findProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category findCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotExistException("Category","not present with","categoryId",categoryId));
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByCategory(findCategory, pageDetails);
        List<Product> productList = page.getContent();
        if(productList.isEmpty()) {
            throw new APIException("Product page","is empty");
        }
        List<ProductDTO> productDTOList = productList.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setResponse(productDTOList);
        productResponse.setPageNumber(page.getNumber());
        productResponse.setPageSize(page.getSize());
        productResponse.setNoOfElements(page.getNumberOfElements());
        productResponse.setLastPage(page.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByProductNameContainingIgnoreCase(keyword, pageDetails);
        List<Product> productList = page.getContent();
        List<ProductDTO> productDTOList = productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setResponse(productDTOList);
        productResponse.setPageNumber(page.getNumber());
        productResponse.setPageSize(page.getSize());
        productResponse.setNoOfElements(page.getNumberOfElements());
        productResponse.setLastPage(page.isLast());
        return productResponse;
    }
//
    @Override
    public APISuccessResponse updateProduct(Long productId, ProductDTO productDTO) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotExistException("Product","not present with","productId",productId));
        findProduct.setProductName(productDTO.getProductName());
        findProduct.setDescription(productDTO.getDescription());
        findProduct.setImage(productDTO.getImage());
        findProduct.setQuantity(productDTO.getQuantity());
        findProduct.setPrice(productDTO.getPrice());
        findProduct.setDiscount(productDTO.getDiscount());
        findProduct.setSpecialPrice(productDTO.getSpecialPrice());
        Product saveProduct = productRepository.save(findProduct);
        return new APISuccessResponse("Product updated successfully",true,modelMapper.map(saveProduct, ProductDTO.class));
    }

    @Override
    public APISuccessResponse deleteProduct(Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotExistException("Product","not present with","productId",productId));
        productRepository.delete(findProduct);
        return new APISuccessResponse("Product deleted successfully",true,modelMapper.map(findProduct, ProductDTO.class));
    }
}
