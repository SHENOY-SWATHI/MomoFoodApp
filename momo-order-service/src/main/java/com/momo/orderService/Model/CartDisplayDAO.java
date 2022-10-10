package com.momo.orderService.Model;

import lombok.Data;

import java.util.List;

@Data
public class CartDisplayDAO{
    private String userEmailId;
    private List<CartItemDAO> cartItemList;
    private Double total;
}
