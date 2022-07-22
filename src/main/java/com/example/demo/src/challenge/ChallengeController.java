package com.example.demo.src.challenge;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.challenge.model.GetChallengeInfoRes;
import com.example.demo.src.challenge.model.GetChallengeRes;
import com.example.demo.src.challenge.model.GetMyChallengeRes;
import com.example.demo.src.diary.model.GetDiaryRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ChallengeProvider challengeProvider;

    public ChallengeController(ChallengeProvider challengeProvider) {
        this.challengeProvider = challengeProvider;

    }

    // 참여가능한 챌린지 리스트
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/diary
    public BaseResponse<List<GetChallengeRes>> getChallenge() {
        try{
            List<GetChallengeRes> getChallengeRes = challengeProvider.getChallenge();
            return new BaseResponse<>(getChallengeRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 사용자가 진행중인 챌린지
    @ResponseBody
    @GetMapping("/mychallenge/{userIdx}") // (GET) 127.0.0.1:9000/diary
    public BaseResponse<List<GetMyChallengeRes>> getMyChallenge(@PathVariable(value = "userIdx") int userIdx) {
        try{
            List<GetMyChallengeRes> getMyChallengeRes = challengeProvider.getMyChallenge(userIdx);
            return new BaseResponse<>(getMyChallengeRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }




    // 사용자가 진행중인 챌린지 세부 정보 페이지
    @ResponseBody
    @GetMapping("/mychallenge/{userIdx}/{challengeIdx}") // (GET) 127.0.0.1:9000/diary
    public BaseResponse<List<GetChallengeInfoRes>> getChallengeInfo(@PathVariable(value = "userIdx") int userIdx, @PathVariable(value = "challengeIdx") int challengeIdx) {
        try{
            List<GetChallengeInfoRes> getChallengeInfoRes = challengeProvider.getChallengeInfo(userIdx, challengeIdx);
            return new BaseResponse<>(getChallengeInfoRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
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

