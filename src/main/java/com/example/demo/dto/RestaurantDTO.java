package com.example.demo.dto;

import jakarta.persistence.Column;


import java.util.Date;
import java.util.List;

public class RestaurantDTO {

    private  int id;

    private String title;

    private String subTitle;

    private String image;

    private boolean isFreeship;

    private String address;

    private double rating;

    private Date openTime;

    List<CategoryDTO> cateroties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public List<CategoryDTO> getCateroties() {
        return cateroties;
    }

    public void setCateroties(List<CategoryDTO> cateroties) {
        this.cateroties = cateroties;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFreeship() {
        return isFreeship;
    }

    public void setFreeship(boolean freeship) {
        isFreeship = freeship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
