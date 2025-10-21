package com.ecommerce.project.exception;

public class APIException extends RuntimeException {

    public APIException(String type) {
        super(type+" list is empty");
    }
}
