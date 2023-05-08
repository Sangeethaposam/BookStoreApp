package com.bookstore.dto;

import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @Email
    private String email;
    private String password;
}
