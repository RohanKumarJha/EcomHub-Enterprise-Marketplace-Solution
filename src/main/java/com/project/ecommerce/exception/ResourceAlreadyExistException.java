package com.project.ecommerce.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String s, String type) {
        super(s+type);
    }
}
