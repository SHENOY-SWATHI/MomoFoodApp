package com.momo.orderService.Service;

import com.momo.orderService.Model.OrderDetails;
import com.momo.orderService.Repository.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class OrderSchedulerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderRepo orderRepo;

    @Scheduled(fixedRateString = "PT2S",initialDelay = 2000)
    public void processOrder() throws InterruptedException{
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(1);
        List<OrderDetails> orderDetails = orderRepo.findAllScheduled('Y', LocalDate.now());
        for(OrderDetails order : orderDetails){
           if (order.getScheduledTime().isBefore(startTime) && order.getScheduledTime().isBefore(endTime)){
               orderRepo.updateStatus(order.getOrderId());
               LOGGER.info("Updated Status of orderId" + order.getOrderId());
           }
        }
    }
}
