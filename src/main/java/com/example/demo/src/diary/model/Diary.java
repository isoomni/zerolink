package com.example.demo.src.diary.model;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Diary {
    private int diaryIdx;
    private String diaryContent;
    private String diaryImg;
    private String diaryDate;
    private List<String> hashtags;
    private String status;
}
