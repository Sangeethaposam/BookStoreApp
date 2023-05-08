package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import com.bookstore.dto.BookDto;
import com.bookstore.entities.BookModel;

public interface IBookService {

	public BookModel insertBook(String token, BookDto bookDto);
    public List<BookModel> getAllBooks();
    public BookModel getBookById(Long bookId);
    public BookModel getBookByName(String bookName);
    public BookModel updateById(String token,Long bookId,BookDto bookDto);
    public List<BookModel> sortAscending();
    public List<BookModel> sortDescending();
    public Optional<BookModel> deleteById(String token,Long bookId);
    public BookModel updateQuantity(String token,Long bookId, int qty);
}

