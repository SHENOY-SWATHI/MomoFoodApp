package com.momo.orderService.Model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long orderId;
    @Column
    private String userId;
    @Column
    private String status;
    @Column
    private char orderScheduled;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updatedDate;
    @Column
    private LocalDate scheduledDate;
    @Column
    private LocalTime scheduledTime;
}
