package com.example.demo.service.impl;

import com.example.demo.dto.RatingFoodDTO;
import com.example.demo.entity.Food;
import com.example.demo.entity.RatingFood;
import com.example.demo.entity.Users;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.RatingFoodRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RatingFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingFoodServiceImpl implements RatingFoodService {
    
    @Autowired
    private RatingFoodRepository ratingFoodRepository;
    
    @Autowired
    private FoodRepository foodRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public RatingFoodDTO addRating(RatingFoodDTO ratingFoodDTO) {
        // Validate user exists
        Users user = userRepository.findById(ratingFoodDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + ratingFoodDTO.getUserId()));
        
        // Validate food exists
        Food food = foodRepository.findById(ratingFoodDTO.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + ratingFoodDTO.getFoodId()));
        
        // Validate rating point (1-5)
        if (ratingFoodDTO.getRatePoint() < 1 || ratingFoodDTO.getRatePoint() > 5) {
            throw new RuntimeException("Rating point must be between 1 and 5");
        }
        
        // Create new rating
        RatingFood rating = new RatingFood();
        rating.setUserId(user);
        rating.setFoodId(food);
        rating.setRatePoint(ratingFoodDTO.getRatePoint());
        rating.setContent(ratingFoodDTO.getContent());
        
        // Save to database
        RatingFood savedRating = ratingFoodRepository.save(rating);
        
        // Convert to DTO and return
        return convertToDTO(savedRating);
    }

    @Override
    public List<RatingFoodDTO> getRatingsForFood(int foodId) {
        // Validate food exists
        foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
        
        // Get all ratings for this food
        List<RatingFood> ratings = ratingFoodRepository.findByFoodIdId(foodId);
        
        // Convert to DTOs
        return ratings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRatingForFood(int foodId) {
        // Validate food exists
        foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));
        
        // Calculate average rating
        Double average = ratingFoodRepository.calculateAverageRating(foodId);
        
        // Return 0.0 if no ratings exist
        return average != null ? Math.round(average * 10.0) / 10.0 : 0.0;
    }

    @Override
    public List<RatingFoodDTO> getRatingsByUser(int userId) {
        // Validate user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        // Get all ratings by this user
        List<RatingFood> ratings = ratingFoodRepository.findByUserIdId(userId);
        
        // Convert to DTOs
        return ratings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Helper method to convert entity to DTO
    private RatingFoodDTO convertToDTO(RatingFood rating) {
        RatingFoodDTO dto = new RatingFoodDTO();
        dto.setId(rating.getId());
        dto.setUserId(rating.getUserId().getId());
        dto.setFoodId(rating.getFoodId().getId());
        dto.setRatePoint(rating.getRatePoint());
        dto.setContent(rating.getContent());
        dto.setUserName(rating.getUserId().getFullName());
        return dto;
    }
}
