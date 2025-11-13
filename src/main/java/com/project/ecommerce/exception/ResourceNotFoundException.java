package com.project.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String type, String id, Long typeId) {
        super(type+id+typeId);
    }
}
