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
        List<GetDiaryRes> getDiaryRes = new ArrayList<>();
        return getDiaryRes;
    }
}
