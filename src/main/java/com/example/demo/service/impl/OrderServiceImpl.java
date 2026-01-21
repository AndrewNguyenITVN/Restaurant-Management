package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.entity.keys.KeyOrderItem;
import com.example.demo.payload.request.OrderRequest;
import com.example.demo.repository.*;
import com.example.demo.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

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

    @Autowired
    PromoRepository promoRepository;

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

            // Tính tổng tiền đơn hàng
            for (Integer foodId : foodIds) {
                Optional<Food> food = foodRepository.findById(foodId);
                if (food.isPresent()) {
                    OrderItem orderItem = new OrderItem();
                    KeyOrderItem key = new KeyOrderItem(savedOrder.getId(), foodId);
                    orderItem.setKeyOrderItem(key);
                    orderItem.setCreateDate(new Date());
                    
                    orderItemRepository.save(orderItem);
                    
                    totalPrice += food.get().getPrice();
                }
            }

            // Áp dụng mã giảm giá nếu có
            double finalPrice = totalPrice;
            double discountAmount = 0;
            
            if (orderRequest.getPromoCode() != null && !orderRequest.getPromoCode().isEmpty()) {
                Optional<Promo> promoOpt = promoRepository.findByCode(orderRequest.getPromoCode().toUpperCase());
                
                if (promoOpt.isPresent()) {
                    Promo promo = promoOpt.get();
                    
                    // Kiểm tra điều kiện áp dụng mã
                    if (isPromoValid(promo, totalPrice, orderRequest.getResId())) {
                        discountAmount = calculateDiscount(promo, totalPrice);
                        finalPrice = totalPrice - discountAmount;
                        
                        // Cập nhật số lần sử dụng
                        promo.setUsedCount(promo.getUsedCount() + 1);
                        promoRepository.save(promo);
                        
                        System.out.println("Promo applied: " + promo.getCode());
                        System.out.println("Discount: " + discountAmount);
                    } else {
                        System.out.println("Promo invalid or not applicable");
                    }
                }
            }

            System.out.println("Total Price: " + totalPrice);
            System.out.println("Final Price after discount: " + finalPrice);
            return true;

        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
            return false;
        }
    }

    /**
     * Kiểm tra mã giảm giá có hợp lệ không
     */
    private boolean isPromoValid(Promo promo, double orderValue, int resId) {
        Date now = new Date();
        
        // Kiểm tra trạng thái active
        if (!promo.isActive()) {
            return false;
        }
        
        // Kiểm tra thời gian hiệu lực
        if (promo.getStartDate() != null && now.before(promo.getStartDate())) {
            return false;
        }
        if (promo.getEndDate() != null && now.after(promo.getEndDate())) {
            return false;
        }
        
        // Kiểm tra giá trị đơn hàng tối thiểu
        if (orderValue < promo.getMinOrderValue()) {
            return false;
        }
        
        // Kiểm tra nhà hàng (nếu promo có giới hạn restaurant)
        if (promo.getResId() != null && promo.getResId().getId() != resId) {
            return false;
        }
        
        // Kiểm tra số lần sử dụng
        if (promo.getUsageLimit() > 0 && promo.getUsedCount() >= promo.getUsageLimit()) {
            return false;
        }
        
        return true;
    }

    /**
     * Tính số tiền giảm giá
     */
    private double calculateDiscount(Promo promo, double orderValue) {
        double discount = 0;
        
        if ("PERCENT".equalsIgnoreCase(promo.getDiscountType())) {
            // Giảm theo phần trăm
            discount = orderValue * (promo.getDiscountValue() / 100);
            
            // Áp dụng giới hạn giảm tối đa (nếu có)
            if (promo.getMaxDiscount() > 0 && discount > promo.getMaxDiscount()) {
                discount = promo.getMaxDiscount();
            }
        } else if ("FIXED".equalsIgnoreCase(promo.getDiscountType())) {
            // Giảm số tiền cố định
            discount = promo.getDiscountValue();
            
            // Không giảm quá tổng đơn hàng
            if (discount > orderValue) {
                discount = orderValue;
            }
        }
        
        return discount;
    }
}
