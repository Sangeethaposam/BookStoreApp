package com.bookstore.service;

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.LoginDto;
import com.bookstore.entities.BookModel;
import com.bookstore.entities.UserModel;
import com.bookstore.exception.UserExceptions;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.utility.UserToken;

@Service
public class BookService implements IBookService{
	List<BookModel> bookModelList = new ArrayList<>();
	@Autowired
	BookRepository bookRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper modelmapper;
	@Autowired
	UserToken jwt;

	@Override
	public BookModel insertBook(String token, BookDto bookDto) {
		LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
		Optional<BookModel> bookModel = bookRepository.findByBookName(bookDto.getBookName());
		if (userModel.get().getRole().toString().equals("user")) {
			throw new UserExceptions("Action restricted to users.");
		}
		if (userModel.get().isLogin() == false) {
			throw new UserExceptions("Admin Not Logged IN.");
		}
		if (bookModel.isPresent()) {
			throw new UserExceptions(bookModel.get().getBookName() + " Book already present.");
		}
		BookModel bookModel2 = modelmapper.map(bookDto, BookModel.class);
		bookModel2.setAdminId(userModel.get().getUserId());
		bookModel2.setAddedDate(LocalDate.now());
		bookRepository.save(bookModel2);
		return bookModel2;
	}

	@Override
	public List<BookModel> getAllBooks() {
		bookModelList = bookRepository.findAll();
		return bookModelList;
	}

	@Override
	public BookModel getBookById(Long bookId) {
		Optional<BookModel> getBookId = bookRepository.findById(bookId);
		if (getBookId == null) {
			throw new UserExceptions("Book with id: '" + getBookId + "' not found");
		} else {
			return getBookId.get();
		}
	}

	@Override
	public BookModel getBookByName(String bookName) {
		Optional<BookModel> bookModel = bookRepository.findByBookName(bookName);
		if (bookModel.isPresent()) {
			return bookModel.get();
		} else {
			throw new UserExceptions("Book with this name: '" + bookName + "' not found");
		}
	}

	@Override
	public BookModel updateById(String token, Long bookId, BookDto bookDto) {
		LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
		Optional<BookModel> book = bookRepository.findById(bookId);
		if (userModel.get().getRole().toString().equals("user")) {
			throw new UserExceptions("Action restricted to Users.");
		}
		if (userModel.get().isLogin() == false) {
			throw new UserExceptions("Admin Not Logged IN.");
		}
		if (book.isEmpty()) {
			throw new UserExceptions("Book Record doesn't exists for this Id: '" + bookId + "'");
		} else {
			BookModel newBook = modelmapper.map(bookDto, BookModel.class);
			newBook.setAddedDate(book.get().getAddedDate());
			newBook.setAdminId(book.get().getAdminId());
			newBook.setLastModifiedDate(LocalDate.now());
			newBook.setBookId(book.get().getBookId());
			bookRepository.save(newBook);
			return newBook;
		}
	}

	@Override
	public List<BookModel> sortAscending() {
		bookModelList = bookRepository.sortAscending();
		return bookModelList;
	}

	@Override
	public List<BookModel> sortDescending() {
		bookModelList = bookRepository.sortDescending();
		return bookModelList;
	}

	@Override
	public Optional<BookModel> deleteById(String token, Long bookId) {
		LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
		Optional<BookModel> bookModel = bookRepository.findById(bookId);
		if (userModel.get().getRole().toString().equals("user")) {
			throw new UserExceptions("Action restricted to Users.");
		}
		if (userModel.get().isLogin() == false) {
			throw new UserExceptions("Admin Not Logged IN.");
		}
		if (bookModel.isPresent()) {
			bookRepository.deleteById(bookId);
			return bookModel;
		} else {
			throw new UserExceptions("Book with Id: '" + bookId + "' doesn't exists");
		}
	}

	@Override
	public BookModel updateQuantity(String token, Long bookId, int qty) {
		LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
		BookModel bookModel = bookRepository.findById(bookId).get();
		if (userModel.get().getRole().toString().equals("user")) {
			throw new UserExceptions("Action restricted to Users.");
		}
		if (userModel.get().isLogin() == false) {
			throw new UserExceptions("Admin Not Logged IN.");
		}
		if (bookModel != null) {
			bookModel.setQuantity(qty);
			bookModel.setLastModifiedDate(LocalDate.now());
			return bookRepository.save(bookModel);
		} else {
			throw new UserExceptions("Quantity not updated for this Id: '" + bookId + "' ");
		}
	}
}
