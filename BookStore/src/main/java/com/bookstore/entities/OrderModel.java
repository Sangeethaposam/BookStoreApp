package com.bookstore.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class OrderModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate orderDate =LocalDate.now();
    private float price;
    private int quantity;
    private String address;
    @OneToOne
    @JoinColumn(name = "user_cart_id")
    private CartModel cartId;
    @ManyToOne
	@JoinColumn(name = "book_id")
	private BookModel bookId;
    private boolean isCancelled=false;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate cancelledDate;
    
    public OrderModel(CartModel user, BookModel book, float price, int quantity, String address) {
        this.price=price;
        this.quantity=quantity;
        this.address=address;
        this.cartId=user;
        this.bookId=book;
    }

}

