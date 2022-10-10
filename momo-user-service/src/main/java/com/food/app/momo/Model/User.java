package com.food.app.momo.Model;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"emailId"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank
    private String firstName;
    @Column
    private String lastName;
    @Column
    private int phoneNumber;
    @Column
    @NotBlank
    @Email(regexp = "^(.+)@(.+)$")
    private String emailId;
    @Column
    @NotBlank
    @Length(min = 6, max = 12)
    private String password;
    @Column
    private String ActiveUser;
    @Column
    private Date createdDate;
    @Column
    private Date updatedDate;

}
