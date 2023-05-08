package com.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookstore.entities.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel,Long> {

	Optional<BookModel> findByBookName(String bookName);
	@Query(value = " select * from book_model order by price asc;",nativeQuery = true)
    List<BookModel> sortAscending();
    @Query(value = " select * from book_model order by price desc;",nativeQuery = true)
    List<BookModel> sortDescending();

}
