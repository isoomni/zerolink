package com.example.demo.src.challenge;


import com.example.demo.config.BaseException;
import com.example.demo.src.challenge.model.GetChallengeInfoRes;
import com.example.demo.src.challenge.model.GetChallengeRes;
import com.example.demo.src.challenge.model.GetMyChallengeRes;
import com.example.demo.src.diary.DiaryDao;
import com.example.demo.src.diary.model.GetDiaryRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ChallengeProvider {

    private final ChallengeDao challengeDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ChallengeProvider(ChallengeDao challengeDao) {
        this.challengeDao = challengeDao;
    }


    // 참여가능한 챌린지 리스트
    public List<GetChallengeRes> getChallenge() throws BaseException {
        try{
            List<GetChallengeRes> getChallengeRes = challengeDao.getChallenge();
            return getChallengeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 사용자가 진행중인 챌린지
    public List<GetMyChallengeRes> getMyChallenge(int userIdx) throws BaseException {
        try{
            List<GetMyChallengeRes> getMyChallengeRes = challengeDao.getMyChallenge(userIdx);
            return getMyChallengeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 사용자가 진행중인 챌린지 세부 정보 페이지
    public List<GetChallengeInfoRes> getChallengeInfo(int userIdx, int challengeIdx) throws BaseException {
        try{
            List<GetChallengeInfoRes> getChallengeInfoRes = challengeDao.getChallengeInfo(userIdx, challengeIdx);
            return getChallengeInfoRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

        return "Success Test";
    }
}
