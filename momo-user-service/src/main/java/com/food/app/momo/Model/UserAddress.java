//package com.food.app.momo.Model;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import java.util.Date;
//
//@Data
//@Entity
//@Table(name = "user_address",uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"userId"})
//})
//public class UserAddress {
//    @Id
//    @Column
//    private long id;
//    @Column
//    @NotBlank
//    private String userId;
//    @Column(length = 50)
//    @NotBlank
//    private String addressLine1;
//    @Column(length = 50)
//    private String addressLine2;
//    @Column
//    @NotBlank
//    private String district;
//    @Column
//    @NotBlank
//    private String state;
//    @Column(length = 6)
//    @NotBlank
//    private String pincode;
//    @Column
//    private Date createdDate;
//    @Column
//    private Date updatedDate;
//    @Column
//    private String addressType;
//}
