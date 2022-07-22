package com.example.demo.src.diary;

import com.example.demo.src.diary.model.Diary;
import com.example.demo.src.diary.model.GetDiaryRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diary")
public class DiaryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final DiaryProvider diaryProvider;
    @Autowired
    private final DiaryService diaryService;

    public DiaryController(DiaryProvider diaryProvider, DiaryService diaryService) {
        this.diaryProvider = diaryProvider;
        this.diaryService = diaryService;
    }

    @GetMapping("") // (GET) 127.0.0.1:9000/diary
    public String getDiaryList(Model model) {
        List<GetDiaryRes> diaries = diaryProvider.getDiaryList();
        model.addAttribute("diaries", diaries);
        for (GetDiaryRes diary : diaries) {
            System.out.println("diary = " + diary);
        }
        return "diary/diary";
    }

    // 공개 일기 세부 조회
    @GetMapping("/{diaryIdx}") // (GET) 127.0.0.1:9000/diary
    public String getDiary(Model model, @PathVariable int diaryIdx, RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();
        if (!diaryProvider.isPublicDiary(diaryIdx)) {
            errors.put("diaryIdx", "허용하지 않는 diaryIdx 입니다.");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            for (String value : errors.values()) {
                System.out.println("value = " + value);
            }
            return "diary/diary";
        }

        GetDiaryRes diary = diaryProvider.getDiary(diaryIdx);
        model.addAttribute("diary", diary);
        System.out.println("diary = " + diary);
        return "diary/detail";
    }

    @GetMapping("/add")
    public String postForm(Model model) {
        model.addAttribute("diary", new Diary());
        return "diary/addDiary";
    }

    @PostMapping("/{userIdx}")
    public String save(@ModelAttribute Diary diary, @PathVariable int userIdx, RedirectAttributes redirectAttributes, Model model) {
        Map<String, String> errors = new HashMap<>();

        if (!StringUtils.hasText(diary.getIsPublic())) {
            diary.setIsPublic("N");
        }

//        if (!errors.isEmpty()) {
//            model.addAttribute("errors", errors);
//            return "diary/addDiary";
//        }

        int diaryIdx = diaryService.save(diary, userIdx);
        redirectAttributes.addAttribute("diaryIdx", diaryIdx);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/diary/{diaryIdx}";
    }

    @GetMapping("/calendar/{userIdx}")
    public String getCalendar(@PathVariable int userIdx, @RequestParam(required = false) int year, @RequestParam(required = false) int month, Model model) {
        Map<String, String> errors = new HashMap<>();
        LocalDate now = LocalDate.now();

        if (year==0) {
            year = now.getYear();
        }
        if (month==0) {
            month = now.getMonthValue();
        }
        // 월 가지고 최대일 결정 (2월이면 윤년 test, 윤년이면 최대일배열=dayDataLeapYear)
        int[] dayData = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] dayDataLeapYear = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        boolean leapTest = isLeapYear(year);
        int dayMax;
        if (month == 2 && leapTest == true) { //2월이고 윤년
            dayMax = dayDataLeapYear[month - 1];
        } else if (month == 2 && leapTest == false) { //2월이지만 윤년X
            dayMax = dayData[month - 1];
        } else {// 2월 아님
            dayMax = dayData[month - 1];
        }
        List<Integer> dates = diaryProvider.getCalendar(userIdx, year, month, dayMax);
        model.addAttribute("dates", dates);
        System.out.println("dates = " + dates);
        return "diary/calendar";
    }

    // 윤년 여부 확인
    public boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            return true;
        else
            return false;
    }


}
