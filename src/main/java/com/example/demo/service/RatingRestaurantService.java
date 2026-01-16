package com.example.demo.service;

import com.example.demo.dto.RatingRestaurantDTO;
import java.util.List;

public interface RatingRestaurantService {
    RatingRestaurantDTO addRating(RatingRestaurantDTO ratingRestaurantDTO);
    List<RatingRestaurantDTO> getRatingsForRestaurant(int resId);
}
