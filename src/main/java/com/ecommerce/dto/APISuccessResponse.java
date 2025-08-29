package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APISuccessResponse {
    private String message;
    private boolean flag;
    private CategoryDTO categoryDTO;
}
