package com.bookstore.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class CartBook {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@ManyToOne
	@JoinColumn(name = "user_cart_id")
	private CartModel cartId;
	@ManyToOne
	@JoinColumn(name = "book_id")
	private BookModel bookId;
	
	private int quantity=1;
	private Long price;
	
	public CartBook(BookModel bookId) {
		this.bookId = bookId;
	}
	
	

}
