package com.momo.orderService.Controller;

import com.momo.orderService.Exceptions.OrderCantBePlaced;
import com.momo.orderService.Model.OrderDisplay;
import com.momo.orderService.Model.OrderHistory;
import com.momo.orderService.Model.CheckoutOrder;
import com.momo.orderService.Service.OrderService;
import com.momo.orderService.Util.OrderPlaceWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/orderService")
public class MomoOrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MomoOrderController.class);

    @Autowired
    OrderService orderService;
    @Autowired
    OrderPlaceWindow orderPlaceWindow;

    @PostMapping(value = "/getOrders")
    public OrderDisplay getOrders(@RequestBody CheckoutOrder checkoutOrder) throws OrderCantBePlaced {
        LOGGER.info("Entered MomoOrderController.getOrders()");
        if(orderPlaceWindow.checkOrderWindow(LocalTime.now())){
            throw new OrderCantBePlaced("Order Window between 11AM - 9pm");
        }
        return orderService.getOrder(checkoutOrder);
    }

    @GetMapping(value = "/cancelOrder/{orderId}")
    public String cancelOrder(@PathVariable long orderId) {
        LOGGER.info("Entered MomoOrderController.cancelOrder()");
        return orderService.cancelOrder(orderId);
    }

    @GetMapping(value = "/getPreviousOrder/{emailId}")
    public List<OrderHistory> previousOrder(@PathVariable String emailId) {
        LOGGER.info("Entered MomoOrderController.previousOrder()");
        return orderService.previousOrder(emailId);
    }

    @PostMapping(value = "/scheduledOrder")
    public OrderDisplay scheduleOrder(@RequestBody CheckoutOrder checkoutOrder) throws OrderCantBePlaced {
        if(orderPlaceWindow.checkOrderWindow(checkoutOrder.getScheduledTime())){
            throw new OrderCantBePlaced("Order Window between 11AM - 9pm");
        }
        orderService.getOrder(checkoutOrder);
        return null;
    }
}
