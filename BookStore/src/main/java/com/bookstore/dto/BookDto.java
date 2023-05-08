package com.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
	private String bookName;
	private String authorName;
	private String bookDescription;
	private String bookImg;
	private long price;
	private int quantity;

}
