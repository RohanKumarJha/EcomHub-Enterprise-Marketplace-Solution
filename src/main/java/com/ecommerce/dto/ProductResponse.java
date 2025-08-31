package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private List<ProductDTO> response;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer noOfElements;
    private boolean lastPage;
}
