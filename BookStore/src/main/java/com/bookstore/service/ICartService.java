package com.bookstore.service;

import java.util.List;

import com.bookstore.dto.CartDto;
import com.bookstore.entities.CartBook;

public interface ICartService {
	public CartBook addToCart(String token, CartDto cartDto);
    public List<CartBook> getAll(String token);
    public List<CartBook> getById(String token);
    public CartBook increment(String token,Long bookId);
    public CartBook decrement(String token,Long bookId);
    public String deleteCartId(String token,long bookId);
}
