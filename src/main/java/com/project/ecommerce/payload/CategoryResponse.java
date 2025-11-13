package com.project.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> categoryDtoList;
    private Integer page_number;
    private Integer page_size;
    private Long total_elements;
    private Integer total_pages;
    private boolean last_page;
}
