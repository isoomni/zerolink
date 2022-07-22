package com.example.demo.src.diary;

import com.example.demo.src.diary.model.Diary;
import com.example.demo.src.diary.model.GetDiaryRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {
    private final DiaryDao diaryDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DiaryService(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }

    public int save(Diary diary, int userIdx) {
        int diaryIdx = diaryDao.save(diary, userIdx);
        return diaryIdx;
    }
}
