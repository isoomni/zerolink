package com.example.demo.src.home;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.src.home.model.GetMenuRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/home")
public class HomeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HomeProvider homeProvider;
    @Autowired
    private final HomeService homeService;
    @Autowired
    private final JwtService jwtService;

    public HomeController(HomeProvider homeProvider, HomeService homeService, JwtService jwtService){
        this.homeProvider = homeProvider;
        this.homeService = homeService;
        this.jwtService = jwtService;
    }

    /**
     * 홈페이지 조회 API
     * [GET] /home
     * @return BaseResponse<GetHomeRes>
     */
    //Query String
    @ResponseBody
    @GetMapping("/") // (GET) 127.0.0.1:9000/home
    public BaseResponse<GetHomeRes> getHome(@RequestParam(required = false) int userIdx) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // Get Users
            GetHomeRes getHomeRes = homeProvider.getHome();
            return new BaseResponse<>(getHomeRes);

        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 메뉴 상세 조회 API
     * [GET] /menu/{menuIdx}
     * @return BaseResponse<GetHomeRes>
     */
    @ResponseBody
    @GetMapping("/menu/{menuIdx}") // (GET) 127.0.0.1:9000/home
    public BaseResponse<GetMenuRes> getMenu(@RequestParam int menuIdx) {
        try{
            // Get Users
            GetMenuRes getMenuRes = homeProvider.getMenu(menuIdx);
            return new BaseResponse<>(getMenuRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
