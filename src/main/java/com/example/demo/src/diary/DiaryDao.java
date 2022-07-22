package com.example.demo.src.diary;

import com.example.demo.src.diary.model.Diary;
import com.example.demo.src.diary.model.GetDiaryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DiaryDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetDiaryRes> getDiaryList() {

        // 다이어리
        String diaryQuery = "SELECT D.diaryIdx, U.userIdx, U.username ,D.diaryContent, D.diaryImg, DATE_FORMAT(D.diaryDate, '%Y-%m-%d %a') AS diaryDate, D.status, D.isPublic\n" +
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

    public GetDiaryRes getDiary(int diaryIdx) {

        // 다이어리
        String diaryQuery = "SELECT D.diaryIdx, U.userIdx, U.username ,D.diaryContent, D.diaryImg, DATE_FORMAT(D.diaryDate, '%Y-%m-%d %a') AS diaryDate, D.status, D.isPublic\n" +
                "FROM Diary D JOIN User U on D.userIdx = U.userIdx\n" +
                "where D.isPublic='Y' AND D.status='Y' AND D.diaryIdx=?;";

        // 해시태그
        String hashtagQuery = "SELECT H.hashtagContent\n" +
                "FROM DiaryHashtag DH JOIN Hashtag H on DH.hashtagIdx = H.hashtagIdx\n" +
                "WHERE diaryIdx=?;\n";

        GetDiaryRes getDiaryRes = this.jdbcTemplate.queryForObject(diaryQuery,
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

                ), diaryIdx);
        return getDiaryRes;
    }

    // 공개 다이어리 여부 확인
    public boolean isPublicDiary(int diaryIdx) {
        String Query = "SELECT EXISTS(SELECT * FROM Diary D where D.isPublic='Y' AND D.status='Y' AND D.diaryIdx=?);";
        int res = this.jdbcTemplate.queryForObject(Query, int.class, diaryIdx);
        if (res == 1) {
            return true;
        } else {
            return false;
        }
    }

    // 다이어리 저장
    public int save(Diary diary, int userIdx) {
        String Query = "INSERT INTO Diary (userIdx, diaryContent, diaryDate, isPublic, diaryImg) VALUES (?,?,?,?,?);";
        Object[] Params = new Object[]{userIdx, diary.getDiaryContent(), diary.getDiaryDate(), diary.getIsPublic(), diary.getDiaryImg()};
        this.jdbcTemplate.update(Query, Params);

        String lastInsertIdQuery = "select last_insert_id()";
        int diaryIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

        String hashtagInsertQuery = "INSERT INTO Hashtag (hashtagContent) VALUES (?);";
        String hashtagMappingQuery = "INSERT INTO DiaryHashtag (diaryIdx, hashtagIdx) VALUES (?, ?);";
        String getHashtagIdxQuery = "SELECT hashtagIdx FROM Hashtag WHERE hashtagContent=?";

        for (String hashtag : diary.getHashtags()) {
            this.jdbcTemplate.update(hashtagInsertQuery, hashtag);
            int hashtagIdx = this.jdbcTemplate.queryForObject(getHashtagIdxQuery, int.class, hashtag);
            this.jdbcTemplate.update(hashtagMappingQuery, diaryIdx, hashtagIdx);
        }

        return diaryIdx;
    }
}
