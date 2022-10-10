package com.momo.cartService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momo.cartService.Controller.MomoCartController;
import com.momo.cartService.Model.*;
import com.momo.cartService.Repository.CartRepo;
import com.momo.cartService.Service.CartService;
import com.momo.cartService.Service.CartserviceImpl;
import com.momo.cartService.Util.BooleanToStringConverter;
import com.momo.cartService.Util.ProductFeignInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartServiceApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceApplicationTests.class);

    @InjectMocks
    private CartserviceImpl cartService;

    @Mock
    private CartRepo cartRepo;

    @InjectMocks
    BooleanToStringConverter booleanToStringConverter;

    @Mock
    private ProductFeignInterface productFeignInterface;

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

    @Test
    public void addToCartTest() throws Exception {
        LOGGER.info("Entered addToCartTest()");
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserEmailId("test@test");
        cartRequest.setCartItemList(cartItemListData());

        MenuItemDAO menuItemDAO = new MenuItemDAO();
        menuItemDAO.setItemName("test");
        menuItemDAO.setCost(40.00);
        menuItemDAO.setMenuItemId("T101");
        menuItemDAO.setMenuId(1);
        menuItemDAO.setDescription("testDescription");
        menuItemDAO.setAvailability(true);

        CartDetails cartDetails = new CartDetails();
        cartDetails.setId(1);
        cartDetails.setUserEmailId("test@test");
        cartDetails.setMenuItemId("T101");
        cartDetails.setItemName("test");
        cartDetails.setItemCost(40.00);

        given(productFeignInterface.getItemByMenuId(anyString())).willReturn(menuItemDAO);
        given(cartRepo.save(any(CartDetails.class))).willReturn(cartDetails);
        List<CartItem> testCartItem = cartService.addToCart(cartRequest);

        assertThat(testCartItem).isNotNull();
        assertThat(testCartItem.size()).isEqualTo(1);
        assertThat(testCartItem.get(0).getMenuItemId()).isEqualTo("T101");
    }

    @Test
    public  void getTotalTest() throws Exception{
        LOGGER.info("Entered getTotalTest()");
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setMenuItemId("T101");
        cartItem.setQuantity(1);
        cartItem.setCost(40.00);
        cartItemList.add(0,cartItem);
        cartItemList.add(1,cartItem);
        Double testTotal = cartService.getTotal(cartItemList);
        assertThat(testTotal).isEqualTo(80);
    }

//    @Test
//    public void viewCartTest() throws Exception {
//        LOGGER.info("Entered viewCartTest()");
//
//        List<CartDetails> cartDetailsList = new ArrayList<>();
//
//        CartDetails cartDetails = new CartDetails();
//        cartDetails.setId(1);
//        cartDetails.setUserEmailId("test@test");
//        cartDetails.setMenuItemId("T101");
//        cartDetails.setItemName("test");
//        cartDetails.setItemCost(40.00);
//
//        cartDetailsList.add(cartDetails);
//
//        when(cartRepo.findAllById(anyString())).thenReturn(cartDetailsList);
//        List<CartItem> cartItemList = new ArrayList<>();
//
//        CartDisplay testCartItem = cartService.viewCart("test@test");
//
//        assertThat(testCartItem).isNotNull();
//        assertThat(testCartItem.getTotal()).isEqualTo(40.00);
//        assertThat(testCartItem.getCartItemList().size()).isEqualTo(1);
//    }

    @Test
    public void removeAllTest() throws Exception {
        LOGGER.info("Entered removeAllTest()");
        String testCartItemString = cartService.removeAll("test@Test");
        assertThat(testCartItemString).isNotNull();
        assertThat(testCartItemString).isEqualTo("Item Removed");
    }

    @Test
    public void convertToDatabaseColumnTest() throws Exception {
        LOGGER.info("Entered convertToDatabaseColumnTest()");
        String test = booleanToStringConverter.convertToDatabaseColumn(true);
        assertThat(test).isEqualTo("Y");
    }

    @Test
    public void convertToEntityAttributeTest() throws Exception {
        LOGGER.info("Entered convertToEntityAttributeTest()");
        Boolean test = booleanToStringConverter.convertToEntityAttribute("N");
        assertThat(test).isEqualTo(false);
    }
}
