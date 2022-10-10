package com.momo.cartService.Model;

import com.momo.cartService.Controller.MomoCartController;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class CartItem {
    private String menuItemId;
    private Integer quantity;
    private double cost;
}
