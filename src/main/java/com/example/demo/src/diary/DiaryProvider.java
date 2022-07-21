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

    public List<GetDiaryRes> getDiary() throws BaseException {
        try{
            List<GetDiaryRes> getDiaryRes = diaryDao.getDiary();
            return getDiaryRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
