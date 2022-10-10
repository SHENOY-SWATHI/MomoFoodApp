package com.momo.orderService.Model;

import lombok.Data;

import java.util.List;
@Data
public class OrderDisplay extends OrderDetails{
    private CartDisplayDAO cartDisplayDAOList;
}
