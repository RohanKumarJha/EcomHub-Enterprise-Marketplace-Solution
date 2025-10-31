package com.project.ecommerce.exception;

public class APIException extends RuntimeException {
    public APIException(String type) {
        super(type);
    }
}
