package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Response;
import com.bookstore.dto.AddressDto;
import com.bookstore.dto.OrderDto;
import com.bookstore.entities.CartBook;
import com.bookstore.entities.OrderModel;
import com.bookstore.service.IOrderService;

@RestController
@RequestMapping("/bookstore")
public class OrderController {
    @Autowired
    IOrderService iorderService;
    @Autowired
   	Response response;
    
    @PostMapping("/placeOrder")
    public ResponseEntity<Response> placeOrder(@RequestBody OrderDto orderDTO,@RequestHeader String token) {
        OrderModel order=iorderService.placeOrder(orderDTO,token);
        response.setMsg("Order details are submitted!");
		response.setObject(order);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
    }
    @PostMapping("/placeOrderByCart")
    public ResponseEntity<Response> placeOrderByCart(@RequestHeader String token,@RequestBody AddressDto address) {
    	List<CartBook> order=iorderService.placeOrderByCart(token,address);
        response.setMsg("Order details are submitted!");
		response.setObject(order);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
    }
    @GetMapping("/getAllOrders")
    public ResponseEntity<Response> getAllOrders(){
        List<OrderModel> orderModelList = iorderService.getAllOrders();
        response.setMsg("Get All orders!");
		response.setObject(orderModelList);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<Response> getOrderById(@PathVariable long orderId){
        OrderModel order = iorderService.getOrderById(orderId);
        response.setMsg("Order Details of Id:"+orderId);
		response.setObject(order);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    @PutMapping("/cancelOrderById/{orderId}")
    public ResponseEntity<Response> cancelOrderById(@PathVariable long orderId,@RequestHeader String token){
    	OrderModel orderModel = iorderService.cancelOrderById(orderId,token);
        response.setMsg("Order cancelled successfully for Id:"+orderId);
		response.setObject("Cancelled order details: "+orderModel);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}

