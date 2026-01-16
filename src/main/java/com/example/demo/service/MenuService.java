package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface MenuService {
    boolean insertMenu(MultipartFile file,
                        String title,
                        String time_ship,
                        Double price,
                        int cate_id);
}
