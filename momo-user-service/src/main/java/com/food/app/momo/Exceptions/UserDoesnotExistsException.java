package com.food.app.momo.Exceptions;

public class UserDoesnotExistsException extends RuntimeException{
    private String message;

    public UserDoesnotExistsException(String message) {
        super(message);
        this.message = message;
    }
}
