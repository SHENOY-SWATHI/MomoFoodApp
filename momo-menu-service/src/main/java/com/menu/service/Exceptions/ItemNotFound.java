package com.menu.service.Exceptions;

public class ItemNotFound extends RuntimeException{

    private final String message;

    public ItemNotFound(String message) {
        super(message);
        this.message = message;
    }
}
