package com.momo.orderService.Service;

import com.momo.orderService.Model.OrderDisplay;
import com.momo.orderService.Model.OrderHistory;
import com.momo.orderService.Model.CheckoutOrder;

import java.util.List;

public interface OrderService {
    OrderDisplay getOrder(CheckoutOrder checkoutOrder);

    String cancelOrder(long orderId);

    List<OrderHistory> previousOrder(String emailId);
}
