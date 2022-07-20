package com.example.demo.src.home.model;

import lombok.*;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userIdx;
    private String username;
    private List<Challenge> challenges;
}
