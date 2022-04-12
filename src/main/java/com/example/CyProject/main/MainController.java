package com.example.CyProject.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    MainNewsApiService mainNewsApiService;

    @GetMapping()
    public String main(Model model) {
        return "main/main";
    }
}
