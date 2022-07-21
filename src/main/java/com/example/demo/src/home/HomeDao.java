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


    public GetHomeRes getHome(int userIdx){
        // 유저
        String getUsersQuery1 = "select userIdx, userName from User where userIdx=?;";

        // 챌린지
        String getUsersQuery2 = "select UC.userIdx, C.challengeName, UC.progress\n" +
                "from UserChallenge UC LEFT JOIN Challenge C ON C.challengeIdx = UC.challengeIdx\n" +
                "where userIdx=?;";

        // 식당
        String getUsersQuery3 = "select R.restaurantIdx, R.closeTime, R.restaurantPhone, R.restaurantName, R.status as restaurantStatus,\n" +
                "       concat(round(6371*acos(cos(radians(?))*cos(radians(R.latitude))*cos(radians(R.longitude)-radians(?))+sin(radians(?))*sin(radians(R.latitude))), 1), 'km') AS distance\n" +
                "from Restaurant R\n" +
                "WHERE R.restaurantIdx=?\n" +
                "HAVING distance <= 10;";

        // 메뉴
        String getMenuQuery4 = "select M.menuIdx, M.menuImg, M.menuName, M.menuQuantity, M.menuOriginalPrice, M.menuDiscountPrice, M.status as menuStatus, R.restaurantIdx\n" +
                "from Restaurant R LEFT JOIN Menu M ON R.restaurantIdx = M.restaurantIdx;";

        // 사용자 위경도
        String getUserLocationQuery = "select latitude, longitude from User where userIdx=?;";

        UserLocation userLocation = this.jdbcTemplate.queryForObject(getUserLocationQuery,
                (rs, rowNum)-> new UserLocation(
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")
                ), userIdx);

        challenges = this.jdbcTemplate.query(getUsersQuery2,
                (rs, rowNum)-> new Challenge(
                        rs.getString("challengeName"),
                        rs.getInt("progress")
                ), userIdx);
        user = this.jdbcTemplate.queryForObject(getUsersQuery1,
                (rs, rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("username"),
                        challenges
                ), userIdx);

        menus = this.jdbcTemplate.query(getMenuQuery4,
                (rs1, rowNum1) -> new Menu(
                        rs1.getInt("menuIdx"),
                        rs1.getString("menuImg"),
                        rs1.getString("menuName"),
                        rs1.getInt("menuQuantity"),
                        rs1.getInt("menuOriginalPrice"),
                        rs1.getInt("menuDiscountPrice"),
                        rs1.getString("menuStatus"),
                        this.jdbcTemplate.queryForObject(getUsersQuery3,
                                (rs2, rowNum2) -> new Restaurant(
                                        rs2.getInt("restaurantIdx"),
                                        rs2.getString("restaurantName"),
                                        rs2.getString("distance"),
                                        rs2.getInt("closeTime"),
                                        rs2.getString("restaurantPhone"),
                                        rs2.getString("restaurantStatus"))
                                , userLocation.getLatitude(), userLocation.getLongitude(), userLocation.getLatitude(), rs1.getInt("restaurantIdx"))
                ));

        return new GetHomeRes(user, menus);
    }

    // 상세화면 조회
    public GetMenuRes getMenu(int menuIdx) {
        // 식당
        String getUsersQuery1 = "select R.restaurantIdx, R.closeTime, R.restaurantPhone, R.restaurantName, R.status as restaurantStatus\n" +
                "from Restaurant R\n" +
                "WHERE R.restaurantIdx=?;";

        // 메뉴
        String getMenuQuery2 = "select M.menuIdx, M.menuImg, M.menuName, M.menuQuantity, M.menuOriginalPrice, M.menuDiscountPrice, M.status as menuStatus, R.restaurantIdx\n" +
                "from Restaurant R LEFT JOIN Menu M ON R.restaurantIdx = M.restaurantIdx\n" +
                "where M.menuIdx=?;";


        Menu menu = this.jdbcTemplate.queryForObject(getMenuQuery2,
                (rs1, rowNum1) -> new Menu(
                        rs1.getInt("menuIdx"),
                        rs1.getString("menuImg"),
                        rs1.getString("menuName"),
                        rs1.getInt("menuQuantity"),
                        rs1.getInt("menuOriginalPrice"),
                        rs1.getInt("menuDiscountPrice"),
                        rs1.getString("menuStatus"),
                        this.jdbcTemplate.queryForObject(getUsersQuery1,
                                (rs2, rowNum2) -> new Restaurant(
                                        rs2.getInt("restaurantIdx"),
                                        rs2.getString("restaurantName"),
                                        rs2.getInt("closeTime"),
                                        rs2.getString("restaurantPhone"),
                                        rs2.getString("restaurantStatus"))
                                , rs1.getInt("restaurantIdx"))
                ), menuIdx);
        return new GetMenuRes(menu);
    }


}
