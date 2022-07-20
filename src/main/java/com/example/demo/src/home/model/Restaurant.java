package com.example.demo.src.home.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Restaurant {
    private int restaurantIdx;
    private String restaurantName;
    private double distance;
    private String closeTime;
    private String restaurantPhone;
    private String status;
}
