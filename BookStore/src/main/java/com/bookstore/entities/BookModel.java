package com.bookstore.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class BookModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String bookName;
    private String authorName;
    private String bookDescription;
    private String bookImg;
    private long price;
    private int quantity;
    private Long adminId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate addedDate =LocalDate.now();
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate lastModifiedDate =LocalDate.now();
}

