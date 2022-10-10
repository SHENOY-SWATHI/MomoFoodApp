package com.momo.cartService.Service;

import com.momo.cartService.Controller.MomoCartController;
import com.momo.cartService.Model.*;
import com.momo.cartService.Repository.CartRepo;
import com.momo.cartService.Util.ProductFeignInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartserviceImpl implements CartService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MomoCartController.class);
    @Autowired
    ProductFeignInterface productFeignInterface;

    @Autowired
    CartRepo cartRepo;
    @Override
    public List<CartItem> addToCart(CartRequest cartRequest) {
        LOGGER.info("Entered CartServiceImpl.addToCart()");
        List<CartItem> cartItemAdded = new ArrayList<>();
        for(CartItem item : cartRequest.getCartItemList()){
            MenuItemDAO menuItem = productFeignInterface.getItemByMenuId(item.getMenuItemId());
            if(menuItem.isAvailability()){
                CartDetails cartDetails = new CartDetails();
                cartDetails.setUserEmailId(cartRequest.getUserEmailId());
                cartDetails.setMenuItemId(menuItem.getMenuItemId());
                cartDetails.setItemCost(menuItem.getCost());
                cartDetails.setItemName(menuItem.getItemName());
                cartDetails.setItemQuantity(item.getQuantity());
                cartRepo.save(cartDetails);
                cartItemAdded.add(item);
            }
        }
        return cartItemAdded;
    }

    @Override
    public Double getTotal(List<CartItem> cartItemDetail) {
        LOGGER.info("Entered CartServiceImpl.getTotal()");
        Double total = cartItemDetail.stream().mapToDouble(cost -> cost.getCost()*cost.getQuantity()).sum();
        return total;
    }

    @Override
    public CartDisplay viewCart(String emailId) {
        LOGGER.info("Entered CartServiceImpl.viewCart()");
        List<CartDetails> cartDetails = cartRepo.findAllById(emailId);
        List<CartItem> itemList = new ArrayList<>();
        cartDetails.stream().forEach(item -> {
            CartItem cartItem = new CartItem();
            cartItem.setMenuItemId(item.getMenuItemId());
            cartItem.setCost(item.getItemCost());
            cartItem.setQuantity(item.getItemQuantity());
            itemList.add(cartItem);
        });
        CartDisplay cartDisplay = new CartDisplay();
        cartDisplay.setUserEmailId(emailId);
        cartDisplay.setCartItemList(itemList);
        cartDisplay.setTotal(getTotal(itemList));

        return cartDisplay;
    }

    @Override
    public CartDisplay removeItem(RemoveItem menuItemId) {
        LOGGER.info("Entered CartServiceImpl.updateItem()");
        cartRepo.removeItem(menuItemId.getMenuItemId(),menuItemId.getUserEmailId());
        return viewCart(menuItemId.getUserEmailId());
    }

    @Override
    public CartDisplay updateItem(CartRequest updateItem) {
        LOGGER.info("Entered CartServiceImpl.updateItem()");
        String userId = updateItem.getUserEmailId();
        for(CartItem item : updateItem.getCartItemList()){
            CartDetails cartDetails = cartRepo.getItem(item.getMenuItemId(),userId);
            cartDetails.setItemQuantity(item.getQuantity());
            cartRepo.save(cartDetails);
        }

        return viewCart(userId);
    }

    @Override
    public String removeAll(String emailId) {
        LOGGER.info("Entered CartServiceImpl.removeAll()");
        cartRepo.removeAllItem(emailId);
        return "Item Removed";
    }
}
