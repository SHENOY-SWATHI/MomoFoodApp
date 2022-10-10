package com.momo.cartService.Model;

import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private String userEmailId;
    private List<CartItem> cartItemList;
}
