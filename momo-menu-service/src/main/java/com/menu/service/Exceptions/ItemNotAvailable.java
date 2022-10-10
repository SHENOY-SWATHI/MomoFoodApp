package com.menu.service.Exceptions;

public class ItemNotAvailable extends Throwable{

    private final String message;

    public ItemNotAvailable(String message) {
        super(message);
        this.message = message;
    }
}
