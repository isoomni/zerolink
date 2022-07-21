package com.example.demo.src.diary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DiaryProvider {

    private final DiaryDao diaryDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DiaryProvider(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }
}
