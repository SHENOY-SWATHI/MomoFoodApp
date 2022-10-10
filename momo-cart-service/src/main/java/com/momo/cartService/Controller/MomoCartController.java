package com.momo.cartService.Controller;

import com.momo.cartService.Model.*;
import com.momo.cartService.Service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartService")
public class MomoCartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MomoCartController.class);

    @Autowired
    CartService cartService;

    @PostMapping(value = "/addToCart")
    public CartDisplay addToCart(@RequestBody CartRequest cartRequest){
        LOGGER.info("Entered MoMoController.addToCart()");
        List<CartItem> cartItemDetail = cartService.addToCart(cartRequest);
        CartDisplay cartDisplay = new CartDisplay();
        cartDisplay.setUserEmailId(cartRequest.getUserEmailId());
        cartDisplay.setCartItemList(cartItemDetail);
        cartDisplay.setTotal(cartService.getTotal(cartItemDetail));
        return cartDisplay;
    }

    @GetMapping(value = "/viewCart/{emailId}")
    public CartDisplay viewCart(@PathVariable String emailId){
        LOGGER.info("Entered MoMoController.viewCart()");
        return cartService.viewCart(emailId);
    }

    @PostMapping (value = "/removeItem")
    public CartDisplay removeItem(@RequestBody RemoveItem removeItem){
        LOGGER.info("Entered MoMoController.removeItem()");
        return cartService.removeItem(removeItem);
    }

    @GetMapping (value = "/removeALL/{emailId}")
    public String removeAll(@PathVariable String emailId){
        LOGGER.info("Entered MoMoController.removeAll()");
        return cartService.removeAll(emailId);
    }

    @PutMapping(value = "/updateQuantity")
    public CartDisplay updateQuantity(@RequestBody CartRequest updateItem){
        LOGGER.info("Entered MoMoController.removeItem()");
        return  cartService.updateItem(updateItem);
    }
}
