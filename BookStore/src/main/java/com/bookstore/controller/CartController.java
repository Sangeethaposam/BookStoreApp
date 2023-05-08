package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Response;
import com.bookstore.dto.CartDto;
import com.bookstore.entities.CartBook;
import com.bookstore.service.ICartService;

@RestController
@RequestMapping("/bookstore")
public class CartController {
    @Autowired
    ICartService cartService;
    @Autowired
	Response response;

    @PostMapping("/addToCart")
    public ResponseEntity<Response> addToCart(@RequestHeader String token, @RequestBody CartDto cartDto){
    	CartBook cartModel = cartService.addToCart(token, cartDto);
        response.setMsg("Successfully added to cart...");
		response.setObject(cartModel);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getAllData")
    public ResponseEntity<Response> getAll(@RequestHeader String token){
        List<CartBook> cartModelList = cartService.getAll(token);
        response.setMsg("All the Data in Cart: ");
		response.setObject(cartModelList);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    @GetMapping("/getById")
    public ResponseEntity<Response> getById(@RequestHeader String token){
    	List<CartBook> cartModel = cartService.getById(token);
        response.setMsg("Cart Data : ");
		response.setObject(cartModel);
		return new ResponseEntity<Response>(response, HttpStatus.FOUND);
    }
    @PutMapping("/increment")
    public ResponseEntity<Response> increment(@RequestHeader String token,@RequestParam Long bookId){
    	CartBook cartModel = cartService.increment(token,bookId);
        response.setMsg("Updated Quantity: ");
		response.setObject(cartModel);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    @PutMapping("/decrement")
    public ResponseEntity<Response> decrement(@RequestHeader String token,@RequestParam Long bookId){
    	CartBook cartModel = cartService.decrement(token,bookId);
        response.setMsg("Updated Quantity: ");
		response.setObject(cartModel);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCartId")
    public ResponseEntity<Response> deleteCartId(@RequestHeader String token,@RequestParam Long bookId){
         cartService.deleteCartId(token,bookId);
        response.setMsg("Data Deleted Successfully...");
		response.setObject(null);
		return new ResponseEntity<Response>(response, HttpStatus.GONE);
    }
}

