package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.FoodDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Food;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RatingFoodRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RatingFoodRepository ratingFoodRepository;

    @Override
    public List<CategoryDTO> getCategoryHome() {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("id"));

        Page<Category> listCategory = categoryRepository.findAll(pageRequest);

        List<CategoryDTO> listCategoryDTO = new ArrayList<>();

        for(Category data :listCategory){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(data.getNameCate());

            List<FoodDTO> listFoodDTO = new ArrayList<>();

            for(Food dataFood: data.getListFood()){
                FoodDTO foodDTO = new FoodDTO();
                foodDTO.setId(dataFood.getId());
                foodDTO.setImage(dataFood.getImage());
                foodDTO.setTitle(dataFood.getTitle());
                foodDTO.setTimeShip(dataFood.getTimeShip());
                foodDTO.setPrice(dataFood.getPrice());
                
                // Tính điểm trung bình và số lượng đánh giá
                Double avgRating = ratingFoodRepository.calculateAverageRating(dataFood.getId());
                Long totalRatings = ratingFoodRepository.countRatingsByFoodId(dataFood.getId());
                foodDTO.setAverageRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0);
                foodDTO.setTotalRatings(totalRatings != null ? totalRatings : 0L);

                listFoodDTO.add(foodDTO);

            }


            categoryDTO.setMenus(listFoodDTO);
            listCategoryDTO.add(categoryDTO);
        }


        return listCategoryDTO;
    }

    @Override
    public boolean addCategory(String name) {
        boolean isSussecc = false;
        Category category = new Category();
        category.setNameCate(name);
        try{
            categoryRepository.save(category);
            isSussecc = true;
        }catch (Exception e){
            System.out.println("Loi add Category " + e);
        }
        return isSussecc;
    }


    @Override
    public boolean updateCategory(int id, String newName) {
        try {
            Category category = categoryRepository.findById(id).orElse(null);
            if (category != null) {
                category.setNameCate(newName);
                categoryRepository.save(category);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Lỗi update Category: " + e.getMessage());
        }
        return false;
    }
}
