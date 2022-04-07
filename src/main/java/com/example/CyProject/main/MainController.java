package com.example.CyProject.main;

import com.example.CyProject.config.AuthenticationFacade;
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
    @Autowired private AuthenticationFacade authenticationFacade;

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        return "main/main";
    }
}
