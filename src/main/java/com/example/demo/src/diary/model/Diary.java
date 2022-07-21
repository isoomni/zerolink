package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Diary {
    private int diaryIdx;
    private String diaryContent;
    private String diaryImg;
    private String diaryDate;
    private List<String> hashtags;
    private String status;
}
