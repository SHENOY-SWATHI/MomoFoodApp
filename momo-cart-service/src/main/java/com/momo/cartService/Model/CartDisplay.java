package com.momo.cartService.Model;

import lombok.Data;

import java.util.List;

@Data
public class CartDisplay extends CartRequest {
    private Double total;
}
