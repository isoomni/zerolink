package com.example.demo.src.home;

import com.example.demo.src.home.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HomeDao {
    private JdbcTemplate jdbcTemplate;
    private User user;
    private List<Challenge> challenges;
    private List<Menu> menus;
    private Restaurant restaurant;



    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetHomeRes getHome(){
        // 유저
        String getUsersQuery1 = "select userIdx, userName from User";

        // 챌린지
        String getUsersQuery2 = "select C.challengeName, UC.progress from UserChallenge UC LEFT JOIN Challenge C ON C.challengeIdx = UC.challengeIdx";

        // 식당, 메뉴
        String getUsersQuery3 = "select M.menuIdx, M.menuImg, M.menuName, M.menuQuantity, M.menuOriginalPrice, M.menuDiscountPrice, M.status as menuStatus,\n" +
                "       R.closeTime, R.restaurantPhone, R.restaurantIdx, R.restaurantName, R.status as restaurantStatus\n" +
                "       concat(round(6371*acos(cos(radians(U.lattitude))*cos(radians(R.lattitude))*cos(radians(R.longitude)-radians(U.longitude))+sin(radians(U.lattitude))*sin(radians(R.lattitude))), 1), 'km') AS distance\n" +
                "from Restaurant R LEFT JOIN Menu M ON R.restaurantIdx = M.restaurantIdx, User U\n" +
                "HAVING distance <= 10;";

        challenges = this.jdbcTemplate.query(getUsersQuery2,
                (rs, rowNum)-> new Challenge(
                        rs.getString("challengeName"),
                        rs.getInt("progress")
                ));

        user = this.jdbcTemplate.queryForObject(getUsersQuery1,
                (rs, rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("username"),
                        challenges
                ));

        restaurant = this.jdbcTemplate.queryForObject(getUsersQuery3,
                (rs, rowNum) -> new Restaurant(
                        rs.getInt("restaurantIdx"),
                        rs.getString("restaurantName"),
                        rs.getDouble("distance"),
                        rs.getString("closeTime"),
                        rs.getString("restaurantPhone"),
                        rs.getString("restaurantStatus"))
                );

        menus = this.jdbcTemplate.query(getUsersQuery3,
                (rs, rowNum) -> new Menu(
                        rs.getInt("menuIdx"),
                        rs.getString("menuImg"),
                        rs.getString("menuName"),
                        rs.getInt("menuQuantity"),
                        rs.getInt("menuOriginalPrice"),
                        rs.getInt("menuDiscountPrice"),
                        rs.getString("menuStatus"),
                        restaurant
                ));

        return new GetHomeRes(user, menus);

    }

    // 상세화면 조회
    public GetMenuRes getMenu(int menuIdx) {
        return new GetMenuRes();
    }
}
