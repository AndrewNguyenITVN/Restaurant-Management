package com.example.demo.service;

import com.example.demo.dto.RatingFoodDTO;

import java.util.List;

public interface RatingFoodService {
    RatingFoodDTO addRating(RatingFoodDTO ratingFoodDTO);
    
    List<RatingFoodDTO> getRatingsForFood(int foodId);
    
    Double getAverageRatingForFood(int foodId);
    
    List<RatingFoodDTO> getRatingsByUser(int userId);
}
