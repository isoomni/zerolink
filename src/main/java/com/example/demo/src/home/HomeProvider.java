package com.example.demo.src.home;

import com.example.demo.config.BaseException;
import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class HomeProvider {

    private final HomeDao homeDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HomeProvider(HomeDao homeDao) {
        this.homeDao = homeDao;
    }

    public GetHomeRes getHome() throws BaseException {
        try{
            GetHomeRes getHomeRes = homeDao.getHome();
            return getHomeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}