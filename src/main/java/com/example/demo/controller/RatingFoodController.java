package com.example.demo.controller;

import com.example.demo.dto.RatingFoodDTO;
import com.example.demo.payload.ResponseData;
import com.example.demo.service.RatingFoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rating-food")
@Tag(name = "Rating Food", description = "API quản lý đánh giá món ăn")
public class RatingFoodController {
    
    @Autowired
    private RatingFoodService ratingFoodService;
    
    @Operation(
        summary = "Thêm đánh giá món ăn",
        description = "User đánh giá một món ăn với điểm số từ 1-5 và nội dung comment"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Đánh giá thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy user hoặc món ăn")
    })
    @PostMapping
    public ResponseEntity<?> addRating(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Thông tin đánh giá món ăn",
                required = true
            )
            @RequestBody RatingFoodDTO ratingFoodDTO) {
        ResponseData responseData = new ResponseData();
        try {
            RatingFoodDTO result = ratingFoodService.addRating(ratingFoodDTO);
            responseData.setData(result);
            responseData.setDesc("Đánh giá món ăn thành công");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            responseData.setSuccess(false);
            responseData.setStatus(400);
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
    
    @Operation(
        summary = "Lấy danh sách đánh giá của món ăn",
        description = "Lấy tất cả các đánh giá của một món ăn cụ thể"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy món ăn")
    })
    @GetMapping("/food/{foodId}")
    public ResponseEntity<?> getRatingsForFood(
            @Parameter(description = "ID của món ăn", example = "1")
            @PathVariable int foodId) {
        ResponseData responseData = new ResponseData();
        try {
            List<RatingFoodDTO> ratings = ratingFoodService.getRatingsForFood(foodId);
            responseData.setData(ratings);
            responseData.setDesc("Lấy danh sách đánh giá thành công");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (RuntimeException e) {
            responseData.setSuccess(false);
            responseData.setStatus(404);
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(
        summary = "Lấy điểm trung bình của món ăn",
        description = "Tính điểm trung bình và tổng số lượng đánh giá của món ăn"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lấy điểm trung bình thành công"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy món ăn")
    })
    @GetMapping("/food/{foodId}/average")
    public ResponseEntity<?> getAverageRating(
            @Parameter(description = "ID của món ăn", example = "1")
            @PathVariable int foodId) {
        ResponseData responseData = new ResponseData();
        try {
            Double average = ratingFoodService.getAverageRatingForFood(foodId);
            Long count = Long.valueOf(ratingFoodService.getRatingsForFood(foodId).size());
            
            Map<String, Object> result = new HashMap<>();
            result.put("foodId", foodId);
            result.put("averageRating", average);
            result.put("totalRatings", count);
            
            responseData.setData(result);
            responseData.setDesc("Lấy điểm trung bình thành công");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (RuntimeException e) {
            responseData.setSuccess(false);
            responseData.setStatus(404);
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(
        summary = "Lấy danh sách đánh giá của user",
        description = "Lấy tất cả các đánh giá mà user đã tạo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy user")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRatingsByUser(
            @Parameter(description = "ID của user", example = "1")
            @PathVariable int userId) {
        ResponseData responseData = new ResponseData();
        try {
            List<RatingFoodDTO> ratings = ratingFoodService.getRatingsByUser(userId);
            responseData.setData(ratings);
            responseData.setDesc("Lấy danh sách đánh giá của user thành công");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (RuntimeException e) {
            responseData.setSuccess(false);
            responseData.setStatus(404);
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(
        summary = "Đánh giá nhanh (dùng query params)",
        description = "API đơn giản để đánh giá món ăn qua query parameters"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Đánh giá thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    })
    @PostMapping("/quick")
    public ResponseEntity<?> addQuickRating(
            @Parameter(description = "ID của user", example = "1")
            @RequestParam int userId,
            @Parameter(description = "ID của món ăn", example = "1")
            @RequestParam int foodId,
            @Parameter(description = "Điểm đánh giá (1-5)", example = "5")
            @RequestParam int ratePoint,
            @Parameter(description = "Nội dung đánh giá", example = "Món ăn rất ngon!")
            @RequestParam(required = false, defaultValue = "") String content) {
        
        ResponseData responseData = new ResponseData();
        try {
            RatingFoodDTO dto = new RatingFoodDTO();
            dto.setUserId(userId);
            dto.setFoodId(foodId);
            dto.setRatePoint(ratePoint);
            dto.setContent(content);
            
            RatingFoodDTO result = ratingFoodService.addRating(dto);
            responseData.setData(result);
            responseData.setDesc("Đánh giá món ăn thành công");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            responseData.setSuccess(false);
            responseData.setStatus(400);
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }
}
