package com.example.demo.exception;

public class ProductNotfoundException extends RuntimeException {

    private static final long serialVersionUID = 2123048368500634063L;

    public ProductNotfoundException(String message) {
        super(message);
    }

}
