package com.example.CyProject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/home")
    public String homeError(Model model) {
        model.addAttribute("err", "미니홈피는 팝업으로만 접근할 수 있습니다.");
        return "home/error";
    }
}