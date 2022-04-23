package com.example.CyProject.main;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MainNewsApiService mainNewsApiService;
    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private MainService mainService;
    @Autowired
    private MessageRepository messageRepository;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String main(Model model, @RequestParam(required = false) String error) {
        if(!"true".equals(error) || error == null) {
            model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
            model.addAttribute("loginUser", authenticationFacade.getLoginUser());
        }
        model.addAttribute("msgCnt", messageRepository.beforeReadMsgCnt(authenticationFacade.getLoginUserPk()));
        return "main/main";
    }

    @PostMapping("friendSearch")
    public String search(@RequestBody String search) {
        mainService.searchUsers(search);
        return "main/search";
    }
}
