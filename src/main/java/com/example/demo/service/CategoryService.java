package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getCategoryHome();
    boolean addCategory(String name);
    boolean updateCategory(int id, String newName);
}
