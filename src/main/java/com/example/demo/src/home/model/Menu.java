package com.example.demo.src.home.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Menu {

    private int menuIdx;
    private String menuImg;
    private String menuName;
    private Integer menuQuantity;
    private Integer menuOriginalPrice;
    private Integer menuDiscountPrice;
    private String status;
    private Restaurant restaurant;
}
