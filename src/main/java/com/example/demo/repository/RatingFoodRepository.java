package com.example.demo.repository;

import com.example.demo.entity.RatingFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingFoodRepository extends JpaRepository<RatingFood, Integer> {
    List<RatingFood> findByFoodIdId(int foodId);
    
    List<RatingFood> findByUserIdId(int userId);
    
    @Query("SELECT AVG(r.ratePoint) FROM rating_food r WHERE r.foodId.id = :foodId")
    Double calculateAverageRating(@Param("foodId") int foodId);
    
    @Query("SELECT COUNT(r) FROM rating_food r WHERE r.foodId.id = :foodId")
    Long countRatingsByFoodId(@Param("foodId") int foodId);
}
