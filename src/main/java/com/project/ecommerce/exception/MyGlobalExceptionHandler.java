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
        Map<String,String> response = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                response.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<ExceptionResponse> myEmptyListException(EmptyListException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> myResourceAlreadyExist(ResourceAlreadyExistException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> myResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),HttpStatus.NOT_FOUND);
    }
}
