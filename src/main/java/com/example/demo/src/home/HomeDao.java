package com.example.demo.src.home;

import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.src.home.model.GetMenuRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class HomeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetHomeRes getHome(){
        String getUsersQuery = "select * from User";
        return new GetHomeRes();
//        return this.jdbcTemplate.queryForObject(getUsersQuery,
//                (rs,rowNum) -> new GetHomeRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"))
//        );
    }

    public GetMenuRes getMenu(int menuIdx) {
        return new GetMenuRes();
    }
}
