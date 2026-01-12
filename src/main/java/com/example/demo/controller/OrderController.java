package com.example.demo.controller;

import com.example.demo.payload.ResponseData;
import com.example.demo.payload.request.OrderRequest;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        ResponseData responseData = new ResponseData();
        
        boolean isSuccess = orderService.placeOrder(orderRequest);
        
        responseData.setSuccess(isSuccess);
        responseData.setData(isSuccess ? "Order placed successfully" : "Failed to place order");
        
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
