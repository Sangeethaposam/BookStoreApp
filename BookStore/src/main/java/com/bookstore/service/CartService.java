package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.CartDto;
import com.bookstore.dto.LoginDto;
import com.bookstore.entities.BookModel;
import com.bookstore.entities.CartBook;
import com.bookstore.entities.UserModel;
import com.bookstore.exception.UserExceptions;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CartRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.utility.UserToken;

@Service
public class CartService implements ICartService{
	@Autowired
    CartRepository cartRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
	ModelMapper modelmapper;
    @Autowired
	UserToken jwt;

    @Override
    public CartBook addToCart(String token, CartDto cartDto){
    	LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
        Optional<BookModel> bookModel = bookRepository.findById(cartDto.bookId.getBookId());
        Optional<CartBook> cartModel=cartRepository.findByBookIdAndUserCartId(cartDto.bookId.getBookId(),userModel.get().getCartId().getCartId());
        if(cartModel.isPresent()) {
        	throw new UserExceptions("Sorry! Book is Already in cart.");
        }
        if(userModel.isPresent() && bookModel.isPresent()){
        	if (userModel.get().isLogin() == false) {
    			throw new UserExceptions(userModel.get().getRole()+" Not Logged IN.");
    		}
            if(0 < bookModel.get().getQuantity()) {
            	CartBook cartBook = new CartBook(bookModel.get());
            	cartBook.setCartId(userModel.get().getCartId());
            	cartBook.setPrice(bookModel.get().getPrice());
                cartRepository.save(cartBook);
                return cartBook;
            }else {
                throw new UserExceptions("Sorry! Book is out of Stock.");
            }
        }else {
            throw new UserExceptions("Either UserId or BookId is not found");
        }
    }
    @Override
    public List<CartBook> getAll(String token) {
    	LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
        List<CartBook> cartModelList = cartRepository.findAll();
        if (userModel.get().getRole().toString().equals("user")) {
			throw new UserExceptions("Action restricted to users.");
		}
        if (userModel.get().isLogin() == false) {
			throw new UserExceptions("Admin Not Logged IN.");
		}
        return cartModelList;
    }

    @Override
    public List<CartBook> getById(String token) {
    	LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
        List<CartBook> cartModel = cartRepository.findAllByUserCartId(userModel.get().getCartId().getCartId());
        if (userModel.get().isLogin() == false) {
			throw new UserExceptions(userModel.get().getRole()+" Not Logged IN.");
		}
        if(!(cartModel.isEmpty())){
            return cartModel;
        }else {
            throw new UserExceptions("Cart Id not found");
        }
    }
    
    @Override
    public CartBook increment(String token,Long bookId){
    	LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
		Optional<BookModel> checkBookId = bookRepository.findById(bookId);
        Optional<CartBook> checkCartId = cartRepository.findByBookIdAndUserCartId(bookId,userModel.get().getCartId().getCartId());
        
        if (userModel.get().isLogin() == false) {
			throw new UserExceptions(userModel.get().getRole()+" Not Logged IN.");
		}
        if (checkCartId.isPresent()) {
            if (checkCartId.get().getQuantity() < checkBookId.get().getQuantity()) {
                checkCartId.get().setQuantity(checkCartId.get().getQuantity()+1);
                checkCartId.get().setPrice(checkBookId.get().getPrice()*checkCartId.get().getQuantity());
                cartRepository.save(checkCartId.get());
                return checkCartId.get();
            } else {
                throw new UserExceptions("we have only "+checkBookId.get().getQuantity()+" Quantity of this Book.");
            }
        }else {
            throw new UserExceptions("BookId: "+checkBookId.get().getBookId()+" not found in your cart.");
        }
    }
    @Override
    public CartBook decrement(String token,Long bookId){
    	LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
		Optional<BookModel> checkBookId = bookRepository.findById(bookId);
        Optional<CartBook> checkCartId = cartRepository.findByBookIdAndUserCartId(bookId,userModel.get().getCartId().getCartId());
        if (userModel.get().isLogin() == false) {
			throw new UserExceptions(userModel.get().getRole()+" Not Logged IN.");
		}
        if (checkCartId.isPresent()) {
            if (checkCartId.get().getQuantity() > 1) {
                checkCartId.get().setQuantity(checkCartId.get().getQuantity()-1);
                checkCartId.get().setPrice(checkBookId.get().getPrice()*checkCartId.get().getQuantity());
                cartRepository.save(checkCartId.get());
                return checkCartId.get();
            } else {
                throw new UserExceptions("Minimum you reached, Now you have to delete this book.");
            }
        }else {
            throw new UserExceptions("BookId: "+checkBookId.get().getBookId()+" not found in your cart.");
        }
    }
    
    @Override
    public String deleteCartId(String token,long bookId){
    	LoginDto email = jwt.decode(token);
		Optional<UserModel> userModel = userRepository.findByEmail(email.getEmail());
		Optional<BookModel> checkBookId = bookRepository.findById(bookId);
        Optional<CartBook> checkCartId = cartRepository.findByBookIdAndUserCartId(bookId,userModel.get().getCartId().getCartId());
        if (userModel.get().isLogin() == false) {
			throw new UserExceptions(userModel.get().getRole()+" Not Logged IN.");
		}
        if(checkCartId.isPresent()){
            cartRepository.deleteById(checkCartId.get().getId());
        }else {
            throw new UserExceptions("BookId: "+checkBookId.get().getBookId()+" not found in your cart.");
        }
        return "BookId"+checkBookId.get().getBookId()+" deleted Successfully..";
    }
}
