package com.example.demo.service.imp;

import com.example.demo.entity.*;
import com.example.demo.entity.keys.keyOrderItem;
import com.example.demo.payload.request.OrderRequest;
import com.example.demo.repository.*;
import com.example.demo.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImp implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FoodRepository foodRepository;

    @Override
    public boolean placeOrder(OrderRequest orderRequest) {
        try {
            Optional<Restaurant> restaurant = restaurantRepository.findById(orderRequest.getResId());
            Optional<Users> user = userRepository.findById(orderRequest.getUserId());

            if (restaurant.isEmpty() || user.isEmpty()) {
                return false;
            }

            Orders orders = new Orders();
            orders.setResId(restaurant.get());
            orders.setUserId(user.get());
            orders.setCreateDate(new Date());

            Orders savedOrder = orderRepository.save(orders);

            List<Integer> foodIds = orderRequest.getFoodIds();
            double totalPrice = 0;

            for (Integer foodId : foodIds) {
                Optional<Food> food = foodRepository.findById(foodId);
                if (food.isPresent()) {
                    OrderItem orderItem = new OrderItem();
                    keyOrderItem key = new keyOrderItem(savedOrder.getId(), foodId);
                    orderItem.setKeyOrderItem(key);
                    orderItem.setCreateDate(new Date());
                    
                    
                    orderItemRepository.save(orderItem);
                    
                    totalPrice += food.get().getPrice();
                }
            }

            System.out.println("Total Price: " + totalPrice);
            return true;

        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
            return false;
        }
    }
}
