package com.project.ecommerce.exception;

import com.project.ecommerce.payload.ExceptionResponse;
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
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                map.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ExceptionResponse> myAPIException(APIException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExist.class)
    public ResponseEntity<ExceptionResponse> myResourceAlreadyExist(ResourceAlreadyExist e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> myResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
