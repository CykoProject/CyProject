package com.example.CyProject.main;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.user.model.friends.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    public String main(Model model, HttpSession hs) {
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        model.addAttribute("loginUser", authenticationFacade.getLoginUser());

        return "main/main";
    }

    @PostMapping("friendSearch")
    public String search(@RequestBody String search) {

        mainService.searchUsers(search);
        return "main/search";
    }
}
