package com.momo.orderService.Service;

import com.momo.orderService.Model.*;
import com.momo.orderService.Repository.OrderHistoryRepo;
import com.momo.orderService.Repository.OrderRepo;
import com.momo.orderService.Util.CartFeignInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderHistoryRepo orderHistoryRepo;

    @Autowired
    CartFeignInterface cartFeignInterface;

    @Override
    public OrderDisplay getOrder(CheckoutOrder checkoutOrder) {
        LOGGER.info("Entered OrderServiceImpl.getOrders()");

        CartDisplayDAO cartItems = cartFeignInterface.viewCart(checkoutOrder.getUserEmailId());
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderScheduled(checkoutOrder.getOrderScheduled());
        orderDetails.setStatus(checkoutOrder.getOrderScheduled()=='N' ? "Active" : "Scheduled");
        orderDetails.setUserId(checkoutOrder.getUserEmailId());
        orderDetails.setCreatedDate(LocalDateTime.now());
        orderDetails.setUpdatedDate(LocalDateTime.now());
        if(checkoutOrder.getScheduledDate()!=null){
            orderDetails.setScheduledDate(checkoutOrder.getScheduledDate());
        }
        if(checkoutOrder.getScheduledTime()!=null){
            orderDetails.setScheduledTime(checkoutOrder.getScheduledTime());
        }
        orderRepo.save(orderDetails);

        createPreviousOrderHistory(cartItems);

        String msg = cartFeignInterface.removeAll(checkoutOrder.getUserEmailId());
        LOGGER.info(msg);

        return getOrderDisplay(cartItems,orderDetails);
    }

    private void createPreviousOrderHistory(CartDisplayDAO cartItems) {
        LOGGER.info("Entered OrderServiceImpl.createPreviousOrderHistory()");
        OrderHistory orderHistory = new OrderHistory();
        for(CartItemDAO item : cartItems.getCartItemList()){
            orderHistory.setUserEmailId(cartItems.getUserEmailId());
            orderHistory.setOrderDate(LocalDateTime.now());
            orderHistory.setMenuItemId(item.getMenuItemId());
            orderHistory.setItemCost(item.getCost());
            orderHistory.setItemQuantity(item.getQuantity());
            orderHistory.setOrderStatus("Ordered");
            orderHistoryRepo.save(orderHistory);
        }
    }

    @Override
    public String cancelOrder(long orderId) {
        LOGGER.info("Entered OrderServiceImpl.cancelOrder()");
        Optional<OrderDetails> orderDetails = orderRepo.findById(orderId);
        LocalTime currentTime = LocalTime.now();

        if (orderDetails.get().getOrderScheduled()=='N'){
            if(orderDetails.get().getCreatedDate().toLocalTime().until(currentTime, ChronoUnit.MINUTES) < 30) {
                return checkDeleteCriteria(orderId,orderDetails.get().getCreatedDate().toLocalDate());
            }
        }else{
            if(orderDetails.get().getStatus()!= "Active"){
                return checkDeleteCriteria(orderId, orderDetails.get().getCreatedDate().toLocalDate());
            }
        }
        return "Order Cannot be Cancelled";
    }

    private String checkDeleteCriteria(long orderId,LocalDate currentDate) {
        String getDate = currentDate.toString();
        orderRepo.deleteById(orderId);
        orderHistoryRepo.updateByDate(getDate);
        return "Order Cancelled";
    }

    @Override
    public List<OrderHistory> previousOrder(String emailId) {
        LOGGER.info("Entered OrderServiceImpl.previousOrder()");
        return orderHistoryRepo.getAllOrders(emailId);
    }

    private OrderDisplay getOrderDisplay(CartDisplayDAO cartItems, OrderDetails orderDetails) {
        LOGGER.info("Entered OrderServiceImpl.getOrderDisplay()");
        OrderDetails getOrderDetails = orderRepo.retriveId(cartItems.getUserEmailId());
        OrderDisplay orderDisplay = new OrderDisplay();
        orderDisplay.setOrderId(getOrderDetails.getOrderId());
        orderDisplay.setOrderScheduled(orderDetails.getOrderScheduled());
        orderDisplay.setStatus(orderDetails.getStatus());
        orderDisplay.setCartDisplayDAOList(cartItems);
        orderDisplay.setCreatedDate(orderDetails.getCreatedDate());
        orderDisplay.setUpdatedDate(orderDetails.getUpdatedDate());
        if(orderDetails.getScheduledDate()!=null){
            orderDisplay.setScheduledDate(orderDetails.getScheduledDate());
        }
        if(orderDetails.getScheduledTime()!=null){
            orderDisplay.setScheduledTime(orderDetails.getScheduledTime());
        }
        return orderDisplay;
    }
}
