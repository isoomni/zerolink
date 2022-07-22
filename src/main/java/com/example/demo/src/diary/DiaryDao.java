package com.example.demo.src.diary;

import com.example.demo.src.diary.model.GetDiaryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DiaryDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetDiaryRes> getDiary() {

        // 다이어리
        String diaryQuery = "SELECT D.diaryIdx, U.userIdx, U.username ,D.diaryContent, D.diaryImg, DATE_FORMAT(D.diaryDate, '%Y-%m-%d %a') AS diaryDate, D.status\n" +
                "FROM Diary D JOIN User U on D.userIdx = U.userIdx\n" +
                "where D.isPublic='Y' AND D.status='Y'\n" +
                "ORDER BY D.createdAt DESC;";

        // 해시태그
        String hashtagQuery = "SELECT H.hashtagContent\n" +
                "FROM DiaryHashtag DH JOIN Hashtag H on DH.hashtagIdx = H.hashtagIdx\n" +
                "WHERE diaryIdx=?;\n";


        List<GetDiaryRes> getDiaryRes = this.jdbcTemplate.query(diaryQuery,
                (rs1, rowNum1) -> new GetDiaryRes(
                        rs1.getInt("userIdx"),
                        rs1.getString("username"),
                        rs1.getInt("diaryIdx"),
                        rs1.getString("diaryContent"),
                        rs1.getString("diaryImg"),
                        rs1.getString("diaryDate"),
                        rs1.getString("status"),
                        this.jdbcTemplate.query(hashtagQuery,
                                (rs2, rowNum2) -> new String (
                                        rs2.getString("hashtagContent")
                                ), rs1.getInt("diaryIdx"))

                ));
        return getDiaryRes;
    }
}
