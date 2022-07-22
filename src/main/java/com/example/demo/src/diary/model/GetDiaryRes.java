package com.example.demo.src.diary.model;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class GetDiaryRes {
    private int userIdx;
    private String username;
    private int diaryIdx;
    private String diaryContent;
    private String diaryImg;
    private String diaryDate;
    private String status;
    private List<String> hashtags;


}
