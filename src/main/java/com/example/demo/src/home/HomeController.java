package com.example.demo.src.home;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.home.model.GetHomeRes;
import com.example.demo.src.home.model.GetMenuRes;
import com.example.demo.src.home.model.Menu;
import com.example.demo.src.home.model.User;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import de.neuland.pug4j.Pug4J;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

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

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hello <b>Spring!</b>");
        return "basic/text-unescaped";
    }


    /**
     * 홈페이지 조회 API
     * [GET] /home
     * @return BaseResponse<GetHomeRes>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/home
    public BaseResponse<List<Menu>> getHome(Model model) throws BaseException {
        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();
        List<Menu> menus = homeProvider.getHome();
        model.addAttribute("menus", menus);
        System.out.println("menus = " + menus);
//        return "home/main";
        return new BaseResponse<>(menus);
    }

    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/home
    public BaseResponse<GetHomeRes> getHome(@PathVariable(value = "userIdx") int userIdx) throws BaseException{
        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            // Get Users
        try {
            User user = homeProvider.getHomeUser(userIdx);
            List<Menu> menus = homeProvider.getHome(userIdx);
//            model.addAttribute("user", user);
//            model.addAttribute("menus", menus);
            GetHomeRes getHomeRes = new GetHomeRes(user, menus);
            return new BaseResponse<>(getHomeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(DATABASE_ERROR);
        }

//        return "home/main";
    }

        /**
     * 메뉴 상세 조회 API
     * [GET] /menu/{menuIdx}
     * @return BaseResponse<GetHomeRes>
     */
    @GetMapping("/menu/{menuIdx}") // (GET) 127.0.0.1:9000/home
    public BaseResponse<GetMenuRes> getMenu(Model model, @PathVariable(value = "menuIdx") int menuIdx) throws BaseException {
        Map<String, String> errors = new HashMap<>();
        GetMenuRes getMenuRes = homeProvider.getMenu(menuIdx);
        model.addAttribute("menu", getMenuRes);
        return new BaseResponse<>(getMenuRes);
//        return "home/showMenu";
    }

    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
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
