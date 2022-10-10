package com.momo.orderService.Util;

import com.momo.orderService.Exceptions.OrderCantBePlaced;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
@Component
public class OrderPlaceWindow {

    LocalTime orderStartTime = LocalTime.of(10,59,59);
    LocalTime orderEndTime = LocalTime.of(21,1,1);
    public boolean  checkOrderWindow(LocalTime time){
        return (time.isBefore(orderStartTime) && time.isAfter(orderEndTime));

    }
}
