package com.food.app.momo.Model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignIn {

    private long id;
    @Email(regexp = "^(.+)@(.+)$")
    private String emailId;
    @NotBlank
    @Length(min = 6, max = 12)
    private String password;
}
