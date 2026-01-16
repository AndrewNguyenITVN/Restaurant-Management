package com.example.demo.controller;


import com.example.demo.payload.ResponseData;
import com.example.demo.service.FileService;
import com.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    FileService fileService;

    @Autowired
    RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<?> createRestaurant(
            @RequestParam MultipartFile file,
            @RequestParam String title,
            @RequestParam String subtitle,
            @RequestParam String description,
            @RequestParam boolean is_freeship,
            @RequestParam String address,
            @RequestParam String open_date
    ){
        ResponseData responseData = new ResponseData();

        boolean isSuccess = restaurantService.insertRestaurant(file, title, subtitle, description, is_freeship, address, open_date);


        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getHomeRestaurant() {

        ResponseData responseData = new ResponseData();


        responseData.setData(restaurantService.getHomePageRestaurant());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFileRestaurant(@PathVariable String filename) {
        Resource resource = fileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getDetailRestaurant(@RequestParam int id) {

        ResponseData responseData = new ResponseData();

        responseData.setData(restaurantService.getDetailRestaurant(id));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/search-restaurant")
    public ResponseEntity<?> searchRestaurantTitle(@RequestParam String title) {

        ResponseData responseData = new ResponseData();

        responseData.setData(restaurantService.getRestaurantByTitle(title));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable int id,
                                              @RequestParam(required = false) MultipartFile file,
                                              @RequestParam String title,
                                              @RequestParam String subtitle,
                                              @RequestParam String description,
                                              @RequestParam boolean is_freeship,
                                              @RequestParam String address,
                                              @RequestParam String open_date) {

        ResponseData responseData = new ResponseData();
        boolean result = restaurantService.updateRestaurant(id, file, title, subtitle, description, is_freeship, address, open_date);
        responseData.setData(result);

        return new ResponseEntity<>(responseData, result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        boolean result = restaurantService.deleteRestaurant(id);
        responseData.setData(result);

        return new ResponseEntity<>(responseData, result ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


}
