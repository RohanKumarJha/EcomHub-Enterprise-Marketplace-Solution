package com.ecommerce.exceptions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String field, String message, String fieldName,String fieldNameFromDTO) {
        super(field+" "+message+" "+fieldName+" "+fieldNameFromDTO);
    }
}
