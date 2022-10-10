package com.momo.cartService.Service;

import com.momo.cartService.Model.*;

import java.util.List;

public interface CartService {
    List<CartItem> addToCart(CartRequest cartRequest);

    Double getTotal(List<CartItem> cartItemDetail);

    CartDisplay viewCart(String emailId);

    CartDisplay removeItem(RemoveItem removeItem);

    CartDisplay updateItem(CartRequest updateItem);

    String removeAll(String emailId);
}
