package com.example.demo.src.diary.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Diary {
    private int diaryIdx;
    private String diaryContent;
    private String diaryImg;
    private Date diaryDate;
    private List<String> hashtags;
    private String isPublic;
    private String status;

    public Diary(String diaryContent, String diaryImg, Date diaryDate, List<String> hashtags, String isPublic) {
        this.diaryContent = diaryContent;
        this.diaryImg = diaryImg;
        this.diaryDate = diaryDate;
        this.hashtags = hashtags;
        this.isPublic = isPublic;
    }
}
