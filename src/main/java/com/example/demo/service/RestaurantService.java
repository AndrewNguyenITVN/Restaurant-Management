package com.example.demo.service;

import com.example.demo.dto.RestaurantDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface RestaurantService {

    boolean insertRestaurant(MultipartFile file,
                              String title,
                              String subtitle,
                              String description,
                              boolean is_freeship,
                              String address,
                              String open_date);

    List<RestaurantDTO> getHomePageRestaurant();
    RestaurantDTO getDetailRestaurant(int id);
    List<RestaurantDTO> getRestaurantByTitle(String title);
    boolean updateRestaurant(int id, MultipartFile file, String title, String subtitle,
                             String description, boolean is_freeship, String address, String open_date);

    boolean deleteRestaurant(int id);
}
