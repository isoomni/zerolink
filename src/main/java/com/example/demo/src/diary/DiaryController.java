package com.example.demo.src.diary;

import com.example.demo.src.diary.model.GetDiaryRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String getDiary(Model model) {
        List<GetDiaryRes> diaries = diaryProvider.getDiary();
        model.addAttribute("diaries", diaries);
        for (GetDiaryRes diary : diaries) {
            System.out.println("diary = " + diary);
        }
        return "diary/diary";
    }
}
