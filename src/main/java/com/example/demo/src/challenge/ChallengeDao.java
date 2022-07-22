package com.example.demo.src.challenge;

import com.example.demo.src.challenge.model.GetChallengeInfoRes;
import com.example.demo.src.challenge.model.GetChallengeRes;
import com.example.demo.src.challenge.model.GetMyChallengeRes;
import com.example.demo.src.diary.model.GetDiaryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChallengeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 참여가능한 챌린지 리스트
    public List<GetChallengeRes> getChallenge() {
        List<GetChallengeRes> getChallengeRes = new ArrayList<>();

        // 유저, 트리
        String getChallengeQuery1 = "";

        // 챌린지 리스트
        String getChallengeQuery2 = "";


        return getChallengeRes;
    }


    // 사용자가 진행중인 챌린지
    public List<GetMyChallengeRes> getMyChallenge(int userIdx) {
        List<GetMyChallengeRes> getMyChallengeRes = new ArrayList<>();

        // 유저, 트리
        String getChallengeQuery1 = "";

        // 챌린지 리스트
        String getChallengeQuery2 = "";

        return getMyChallengeRes;
    }


    // 사용자가 진행중인 챌린지 세부 정보 페이지
    public List<GetChallengeInfoRes> getChallengeInfo(int userIdx, int challengeIdx) {
        List<GetChallengeInfoRes> getChallengeInfoRes = new ArrayList<>();

        // 유저, 트리
        String getChallengeQuery1 = "";

        // 챌린지 리스트
        String getChallengeQuery2 = "";

        // 다이어리 리스트
        String getChallengeQuery3 = "";

        return getChallengeInfoRes;
    }



}
