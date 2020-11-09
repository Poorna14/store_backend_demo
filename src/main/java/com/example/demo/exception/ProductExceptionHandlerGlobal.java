package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductExceptionHandlerGlobal {

    @ExceptionHandler(value = ProductNotfoundException.class)
    public @ResponseBody
    ResponseEntity<Object> handleProductNotfoundException(ProductNotfoundException exception) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("message", exception.getMessage());
        errorInfo.put("status", HttpStatus.NOT_FOUND);
        errorInfo.put("status_code", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }
}
