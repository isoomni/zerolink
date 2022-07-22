package com.example.demo.src.home;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.home.model.GetMenuRes;
import com.example.demo.src.home.model.Menu;
import com.example.demo.src.home.model.User;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@Controller
//@RequestMapping("/home")
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
//    @ResponseBody
    @GetMapping("/home/{userIdx}") // (GET) 127.0.0.1:9000/home
    public String getHome(Model model, @PathVariable(value = "userIdx") int userIdx) throws BaseException {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // Get Users
            System.out.println("userIdx = " + userIdx);
            User user = homeProvider.getHomeUser(userIdx);
            List<Menu> menus = homeProvider.getHome(userIdx);
            model.addAttribute("user", user);
            model.addAttribute("menus", menus);
            return "home";
    }

        /**
     * 메뉴 상세 조회 API
     * [GET] /menu/{menuIdx}
     * @return BaseResponse<GetHomeRes>
     */
    @ResponseBody
    @GetMapping("/home/menu/{menuIdx}") // (GET) 127.0.0.1:9000/home
    public BaseResponse<GetMenuRes> getMenu(@PathVariable(value = "menuIdx") int menuIdx) {
        try{
            // Get Users
            GetMenuRes getMenuRes = homeProvider.getMenu(menuIdx);
            return new BaseResponse<>(getMenuRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/home/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

        return "Success Test";
    }

}
