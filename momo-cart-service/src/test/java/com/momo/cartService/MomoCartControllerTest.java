package com.momo.cartService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momo.cartService.Controller.MomoCartController;
import com.momo.cartService.Model.*;
import com.momo.cartService.Repository.CartRepo;
import com.momo.cartService.Service.CartService;
import com.momo.cartService.Util.ProductFeignInterface;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MomoCartController.class)
public class MomoCartControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceApplicationTests.class);

    @Autowired
    private MomoCartController momoController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private CartRepo cartRepo;

    @Test
    public void viewCartTest() throws Exception {
        LOGGER.info("Entered in viewCartTest()");
        CartDisplay cartDisplay = cartDisplayData();

        when(cartService.viewCart(anyString())).thenReturn(cartDisplay);

        mockMvc.perform(MockMvcRequestBuilders.get("/cartService/viewCart/test@test.mail"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.userEmailId")).value("test@test"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.cartItemList")).exists());
    }
    @Test
    public void addCartTest() throws Exception {
        LOGGER.info("Entered in addCartTest()");
        List<CartDetails> cartDetailsList = new ArrayList<>();
        CartDetails cartDetails = new CartDetails();
        cartDetails.setId(1);
        cartDetails.setUserEmailId("test@test");
        cartDetails.setMenuItemId("T101");
        cartDetails.setItemName("test");
        cartDetails.setItemCost(40.00);
        cartDetailsList.add(cartDetails);

        CartDisplay cartDisplay = cartDisplayData();
        List<CartItem> cartItemList = cartItemListData();

        when(cartService.addToCart(any(CartRequest.class))).thenReturn(cartItemList);
        when(cartService.getTotal(cartItemList)).thenReturn(40.00);

        mockMvc.perform(MockMvcRequestBuilders.post("/cartService/addToCart")
                        .content(new ObjectMapper().writeValueAsString(cartItemList))
                        .content(new ObjectMapper().writeValueAsString(cartDisplay))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(3))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.userEmailId")).value("test@test"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.cartItemList")).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.total")).value(40.00));

    }

    @Test
    public void removeCartTest() throws Exception {
        LOGGER.info("Entered in removeCartTest()");
        CartDisplay cartDisplay = cartDisplayData();

        when(cartService.removeItem(any(RemoveItem.class))).thenReturn(cartDisplay);

        mockMvc.perform(MockMvcRequestBuilders.post("/cartService/removeItem")
                .content(new ObjectMapper().writeValueAsString(cartDisplay))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(3))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.userEmailId")).value("test@test"))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.cartItemList")).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.total")).value(40.00));
    }

    @Test
    public void updateTest() throws Exception {
        LOGGER.info("Entered in removeCartTest()");
        CartDisplay cartDisplay = cartDisplayData();
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserEmailId("test@test");
        cartRequest.setCartItemList(cartItemListData());

        when(cartService.updateItem(any(CartRequest.class))).thenReturn(cartDisplay);

        mockMvc.perform(MockMvcRequestBuilders.put("/cartService/updateQuantity")
                        .content(new ObjectMapper().writeValueAsString(cartDisplay))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.size()")).value(3))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.userEmailId")).value("test@test"));

        LOGGER.info("Entered in viewCartTest()");
    }
    @Test
    public void removeAllCartTest() throws Exception {
        LOGGER.info("Entered in removeAllCartTest()");
        RemoveItem removeItem = new RemoveItem();
        removeItem.setMenuItemId("T101");
        removeItem.setUserEmailId("test@test");

        when(cartService.removeAll(any())).thenReturn("Item Removed");

        mockMvc.perform(MockMvcRequestBuilders.get("/cartService/removeALL/test@test"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Item Removed"));
        LOGGER.info("Entered in viewCartTest()");
    }
    private CartDisplay cartDisplayData() {
        LOGGER.info("Creating Dummy CartDisplay");
        List<CartItem> cartItemList = cartItemListData();
        CartDisplay cartDisplay = new CartDisplay();
        cartDisplay.setTotal(40.00);
        cartDisplay.setUserEmailId("test@test");
        cartDisplay.setCartItemList(cartItemList);
        return cartDisplay;
    }

    private List<CartItem> cartItemListData() {
        LOGGER.info("Creating Dummy cartItemListData");
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setMenuItemId("T101");
        cartItem.setQuantity(1);
        cartItem.setCost(40.00);
        cartItemList.add(cartItem);
        return  cartItemList;
    }

}
