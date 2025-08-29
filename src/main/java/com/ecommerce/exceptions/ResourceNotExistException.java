package com.ecommerce.exceptions;

public class ResourceNotExistException extends RuntimeException {
    public ResourceNotExistException(String field, String message, String fieldName,Long fieldNameFromDTO) {
        super(field+" "+message+" "+fieldName+" : "+fieldNameFromDTO);
    }
}
