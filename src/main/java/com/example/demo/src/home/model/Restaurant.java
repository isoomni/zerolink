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
    private double distance;
    private String closeTime;
    private String restaurantPhone;
    private String restaurantStatus;
}
