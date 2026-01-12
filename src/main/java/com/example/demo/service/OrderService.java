package com.example.demo.service;

import com.example.demo.payload.request.OrderRequest;

public interface OrderService {
    boolean placeOrder(OrderRequest orderRequest);
}
