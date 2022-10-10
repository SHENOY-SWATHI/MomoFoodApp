package com.momo.orderService.Model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CheckoutOrder {
    private String userEmailId;
    private char orderScheduled;
    private LocalTime scheduledTime;
    private LocalDate scheduledDate;
}
