package com.example.CyProject.home;

import com.example.CyProject.home.model.HomeEntity;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.visit.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    @Autowired private DiaryRepository diaryRepository;
    @Autowired private VisitRepository visitRepository;

    @GetMapping
    public String home() {
        return "home/index";
    }

    @GetMapping("/diary")
    public String diary(HomeEntity entity, Model model) {
        model.addAttribute("data", diaryRepository.findByIhostOrderByRdtDesc(entity.getIuser()));
        return "home/diary/diary";
    }

    @GetMapping("/diary/write")
    public String writeDiary(int iuser) {
        // todo 본인 미니홈피가 아닐 경우 redirect
        return "home/diary/writediary";
    }
    @PostMapping("/diary/write")
    public String writeDiaryProc(DiaryEntity entity) {
        // todo write.html -> 로그인 되어 있는 사람 iuser, 로그인 되어 있는 사람 iuser랑 미니홈피 iuser 비교
        diaryRepository.save(entity);
        return "redirect:/home/diary?iuser=" + entity.getIhost();
    }

    @GetMapping("/visit")
    public String visit(int iuser, Model model) {
        model.addAttribute("data", visitRepository.findByIhostOrderByRdtDesc(iuser));
        return "home/visit/visit";
    }
}