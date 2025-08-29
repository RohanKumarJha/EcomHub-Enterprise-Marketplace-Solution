package com.ecommerce.exceptions;

public class APIException extends RuntimeException {
    public APIException(String fieldName, String isEmpty) {
        super(fieldName+" "+isEmpty);
    }
}
