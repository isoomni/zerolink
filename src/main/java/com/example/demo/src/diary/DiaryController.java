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


}
