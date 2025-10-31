package com.project.ecommerce.exception;

public class ResourceAlreadyExist extends RuntimeException {
    public ResourceAlreadyExist(String type,String typeName) {
        super(type+" "+typeName);
    }
}
