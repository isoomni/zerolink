package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.src.diary.model.GetDiaryRes;
import com.example.demo.src.home.model.GetHomeRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class DiaryProvider {

    private final DiaryDao diaryDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DiaryProvider(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }

    public List<GetDiaryRes> getDiaryList(){
        List<GetDiaryRes> getDiaryRes = diaryDao.getDiaryList();
        return getDiaryRes;

    }


    public GetDiaryRes getDiary(int diaryIdx) {
        GetDiaryRes getDiaryRes = diaryDao.getDiary(diaryIdx);
        return getDiaryRes;
    }

    // 공개 다이어리 여부 확인
    public boolean isPublicDiary(int diaryIdx) {
        boolean isPublicDiary = diaryDao.isPublicDiary(diaryIdx);
        return isPublicDiary;
    }

    public List<Integer> getCalendar(int userIdx, int year, int month, int dayMax) {
        List<Integer> dates = diaryDao.getCalendar(userIdx, year, month);
        return dates;
    }
}
