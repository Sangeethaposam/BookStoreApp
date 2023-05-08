package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entities.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel,Long>{

}
