package com.example.demo.service.impl;

import com.example.demo.dto.RatingRestaurantDTO;
import com.example.demo.entity.RatingRestaurant;
import com.example.demo.entity.Restaurant;
import com.example.demo.entity.Users;
import com.example.demo.repository.RatingRestaurantRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RatingRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingRestaurantServiceImpl implements RatingRestaurantService {
    @Autowired
    RatingRestaurantRepository ratingRestaurantRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public RatingRestaurantDTO addRating(RatingRestaurantDTO ratingRestaurantDTO) {
        Users user = userRepository.findById(ratingRestaurantDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(ratingRestaurantDTO.getResId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        RatingRestaurant rating = new RatingRestaurant();
        rating.setUserId(user);
        rating.setResId(restaurant);
        rating.setRatePoint(ratingRestaurantDTO.getRatePoint());
        rating.setContent(ratingRestaurantDTO.getContent());

        RatingRestaurant saved = ratingRestaurantRepository.save(rating);
        // Chuyển thành DTO trả về
        RatingRestaurantDTO resp = new RatingRestaurantDTO();
        resp.setUserId(user.getId());
        resp.setResId(restaurant.getId());
        resp.setRatePoint(saved.getRatePoint());
        resp.setContent(saved.getContent());
        return resp;
    }

    @Override
    public List<RatingRestaurantDTO> getRatingsForRestaurant(int resId) {
        List<RatingRestaurant> list = ratingRestaurantRepository.findByResIdId(resId);
        return list.stream().map(r -> {
            RatingRestaurantDTO dto = new RatingRestaurantDTO();
            dto.setUserId(r.getUserId().getId());
            dto.setResId(r.getResId().getId());
            dto.setRatePoint(r.getRatePoint());
            dto.setContent(r.getContent());
            return dto;
        }).collect(Collectors.toList());
    }
}
