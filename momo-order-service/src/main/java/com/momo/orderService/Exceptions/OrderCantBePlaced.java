package com.momo.orderService.Exceptions;

public class OrderCantBePlaced extends Throwable{

    private final String message;

    public OrderCantBePlaced(String message) {
        super(message);
        this.message = message;
    }
}
