package com.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.CartBook;

@Repository
public interface CartRepository extends JpaRepository<CartBook,Long> {

	@Query(value = "select * from bookStore.cart_book where user_cart_id=:cartId",nativeQuery = true)
	List<CartBook> findAllByUserCartId(Long cartId);

	@Query(value = "select * from bookStore.cart_book where user_cart_id=:cartId && book_id=:bookId",nativeQuery = true)
	Optional<CartBook> findByBookIdAndUserCartId(Long bookId, Long cartId);

}

