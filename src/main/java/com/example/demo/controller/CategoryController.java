package com.example.demo.controller;

import com.example.demo.payload.ResponseData;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getHomeCategory(){

        ResponseData responseData = new ResponseData();


        responseData.setData(categoryService.getCategoryHome());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@RequestParam String nameCate) {
        ResponseData responseData = new ResponseData();

        responseData.setData(categoryService.addCategory(nameCate));

        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestParam String nameCate) {
        ResponseData responseData = new ResponseData();

        boolean result = categoryService.updateCategory(id, nameCate);
        responseData.setData(result);

        return new ResponseEntity<>(responseData, result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


}
