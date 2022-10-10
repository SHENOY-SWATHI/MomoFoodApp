package com.momo.cartService.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CartDetails {
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
    private String itemName;
    @Column
    private Integer itemQuantity;

}
