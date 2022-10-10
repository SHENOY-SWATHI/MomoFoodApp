package com.food.app.momo.Exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    private String message;

    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
