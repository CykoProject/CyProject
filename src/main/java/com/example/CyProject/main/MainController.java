package com.example.CyProject.main;

import com.example.CyProject.config.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    MainNewsApiService mainNewsApiService;
    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    MainService mainService;

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("loginUser", authenticationFacade.getLoginUser());

        return "main/main";
    }

    @PostMapping("friendSearch")
    public String search(@RequestBody String search) {

        mainService.searchUsers(search);
        return "main/search";
    }
    @RequestMapping(value = {"main"},method = {RequestMethod.POST})
    public String login(){
        return "main/main";
    }
}
