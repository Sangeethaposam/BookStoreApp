package com.bookstore.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Email;

import com.bookstore.entities.CartModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    @Email
    @Column(unique=true)
    private String email;
    private String password;
    private LocalDate dob;
    private String address;
    private CartModel cartId;
}