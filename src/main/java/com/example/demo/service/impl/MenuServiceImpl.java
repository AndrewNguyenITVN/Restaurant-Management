package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.Food;
import com.example.demo.repository.FoodRepository;
import com.example.demo.service.FileService;
import com.example.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    FileService fileService;

    @Autowired
    FoodRepository foodRepository;

    @Override
    public boolean insertMenu(MultipartFile file, String title, String time_ship, Double price, int cate_id) {
        boolean isInsertSuccess = false;
        try{
            boolean isSave = fileService.savefile(file);
            if(isSave){
                Food food = new Food();
                food.setTitle(title);
                food.setPrice(price);
                food.setTimeShip(time_ship);
                food.setImage(file.getOriginalFilename());
                food.setTimeShip(time_ship);
                Category category = new Category();
                category.setId(cate_id);
                food.setCateId(category);
                foodRepository.save(food);
                isInsertSuccess = true;
            }
        }catch(Exception e){
            System.out.println("Loi crate menu " + e.getMessage());
        }
        return isInsertSuccess;
    }
}
