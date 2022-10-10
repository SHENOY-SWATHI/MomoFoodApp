package com.momo.orderService.Model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String userEmailId;
    @Column
    private String menuItemId;
    @Column
    private double itemCost;
    @Column
    private Integer itemQuantity;
    @Column
    private  String orderStatus;
    @Column
    private LocalDateTime orderDate;
}
