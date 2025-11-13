package com.project.ecommerce.exception;

public class EmptyListException extends RuntimeException {
    public EmptyListException(String type) {
        super(type);
    }
}
