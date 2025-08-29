package com.ecommerce.exceptions;

import com.ecommerce.dto.APIExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            response.put(error.getObjectName(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIExceptionResponse> myApiException(APIException e) {
        APIExceptionResponse apiExceptionResponse = new APIExceptionResponse(e.getMessage(),false);
        return new ResponseEntity<>(apiExceptionResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<APIExceptionResponse> myResourceAlreadyExistException(ResourceAlreadyExistException e) {
        APIExceptionResponse apiExceptionResponse = new APIExceptionResponse(e.getMessage(), false);
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotExistException.class)
    public ResponseEntity<APIExceptionResponse> myResourceNotExistException(ResourceNotExistException e) {
        APIExceptionResponse apiExceptionResponse = new APIExceptionResponse(e.getMessage(), false);
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    }
}
