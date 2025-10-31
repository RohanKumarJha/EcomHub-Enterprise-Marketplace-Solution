package com.project.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String type, String typeId, Long typeIdVal) {
        super(type+" not exist with "+typeId+" "+typeIdVal);
    }
}
