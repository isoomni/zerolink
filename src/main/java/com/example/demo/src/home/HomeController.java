package com.example.demo.src.home;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.home.model.GetHomeRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HomeProvider homeProvider;
    @Autowired
    private final HomeService homeService;

    public HomeController(HomeProvider homeProvider, HomeService homeService){
        this.homeProvider = homeProvider;
        this.homeService = homeService;
    }

    /**
     * 홈페이지 조회 API
     * [GET] /home
     * [GET] /home?userIdx
     * @return BaseResponse<List<GetHomeRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/home
    public BaseResponse<List<GetHomeRes>> getHome(@RequestParam(required = false) int userIdx) {
        try{
            // Get Users
            List<GetHomeRes> getUsersRes = homeProvider.getHome();
            return new BaseResponse<>(getUsersRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
