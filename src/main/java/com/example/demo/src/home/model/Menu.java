package com.example.demo.src.home.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    private int menuIdx;
    private String menuImg;
    private String menuName;
    private int menuQuantity;
    private int menuOriginalPrice;
    private int menuDiscountPrice;
    private String menuStatus;
    private Restaurant restaurant;
}
