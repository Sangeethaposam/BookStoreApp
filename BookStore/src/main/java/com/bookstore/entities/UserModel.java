package com.bookstore.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserModel {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long userId;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String password;
	    private LocalDate dob;
	    private String address;
	    private String role="user";
	    private boolean isLogin=false;
	    
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "user_cart_id")
	    private CartModel cartId;
}

