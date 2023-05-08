package com.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Response;
import com.bookstore.dto.BookDto;
import com.bookstore.entities.BookModel;
import com.bookstore.exception.UserExceptions;
import com.bookstore.service.IBookService;

@RestController
@RequestMapping("/bookstore")
public class BookController {
	@Autowired
	IBookService iBookService;
	@Autowired
	Response response;

	@PostMapping("/insertBook")
	public ResponseEntity<Response> insertBook(@RequestHeader String token, @RequestBody BookDto bookDto)
			throws UserExceptions {
		BookModel bookModel = iBookService.insertBook(token, bookDto);
		response.setMsg("New Book Added to Collection: " + bookModel.getBookName());
		response.setObject(bookModel);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	@GetMapping("/getAllBooks")
	public ResponseEntity<Response> getAllBooks() {
		List<BookModel> bookModelList = iBookService.getAllBooks();
		response.setMsg("List of Books: ");
		response.setObject(bookModelList);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getBookById/{bookId}")
	public ResponseEntity<Response> getBookById(@PathVariable Long bookId) {
		BookModel bookModel = iBookService.getBookById(bookId);
		response.setMsg("Book with Id: " + bookModel.getBookId());
		response.setObject(bookModel);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getBookByName")
	public ResponseEntity<Response> getBookByName(@RequestParam String bookName) {
		BookModel bookModel = iBookService.getBookByName(bookName);
		response.setMsg(" Details of Book: " + bookName);
		response.setObject(bookModel);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateById/{bookId}")
	public ResponseEntity<Response> updateById(@RequestHeader String token, @PathVariable Long bookId,
			@RequestBody BookDto bookDto) throws UserExceptions {
		BookModel bookModel = iBookService.updateById(token, bookId, bookDto);
		response.setMsg("Data updated of id: " + bookId);
		response.setObject(bookModel);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@GetMapping("/sortAscending")
	public ResponseEntity<Response> sortAscending() {
		List<BookModel> bookListAsc = iBookService.sortAscending();
		response.setMsg("List of Books from Low to High: ");
		response.setObject(bookListAsc);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@GetMapping("/sortDescending")
	public ResponseEntity<Response> sortDescending() {
		List<BookModel> bookListDesc = iBookService.sortDescending();
		response.setMsg("List of Books from High to Low: ");
		response.setObject(bookListDesc);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteById/{bookId}")
	public ResponseEntity<Response> deleteById(@RequestHeader String token, @PathVariable Long bookId) {
		Optional<BookModel> bookModel = iBookService.deleteById(token, bookId);
		response.setMsg(" Details of Deleted Book: " + bookId);
		response.setObject(bookModel.get());
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateQuantity/{bookId}")
	public ResponseEntity<Response> updateQuantity(@RequestHeader String token, @PathVariable Long bookId,
			@RequestParam int qty) throws UserExceptions {
		BookModel bookModel = iBookService.updateQuantity(token, bookId, qty);
		response.setMsg("Data(Quantity) updated of id: " + bookId);
		response.setObject(bookModel);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}
}

