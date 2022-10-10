package com.momo.orderService.Model;

import lombok.Data;

@Data
public class CartItemDAO {
    private String menuItemId;
    private Integer quantity;
    private double cost;
}
