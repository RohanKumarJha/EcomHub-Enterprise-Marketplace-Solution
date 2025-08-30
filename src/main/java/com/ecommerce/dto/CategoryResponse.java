package com.ecommerce.dto;

import com.ecommerce.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> response;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer noOfElements;
    private boolean lastPage;
}
