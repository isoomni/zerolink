package com.example.demo.src.home.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    private int restaurantIdx;
    private String restaurantName;
    private String distance = null;
    private int closeTime;
    private String restaurantPhone;
    private String restaurantStatus;


    public Restaurant(int restaurantIdx, String restaurantName, int closeTime, String restaurantPhone, String restaurantStatus) {
        this.restaurantIdx = restaurantIdx;
        this.restaurantName = restaurantName;
        this.closeTime = closeTime;
        this.restaurantPhone = restaurantPhone;
        this.restaurantStatus = restaurantStatus;
    }
}
