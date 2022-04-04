package com.example.CyProject.home;

import com.example.CyProject.home.model.HomeEntity;
import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.diary.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    @Autowired private DiaryRepository diaryRepository;

    @GetMapping
    public String home() {
        return "home/index";
    }

    @GetMapping("/diary")
    public String diary(HomeEntity entity, Model model) {
        model.addAttribute("data", diaryRepository.findByIhostOrderByRdtDesc(entity.getIuser()));
        return "home/diary";
    }
}