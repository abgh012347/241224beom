package com.example.demo_db.exception;

public class CookdbAuthenticationExceptionHandler extends RuntimeException{
    public CookdbAuthenticationExceptionHandler(String message) {
        super(message);
    }
}
