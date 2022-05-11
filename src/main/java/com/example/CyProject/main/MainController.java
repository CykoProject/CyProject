package com.example.CyProject.main;

import com.example.CyProject.PageEntity;
import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.home.model.visit.VisitorEntity;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.friends.FriendsEntity;
import com.example.CyProject.user.model.friends.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private Utils utils;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String main(Model model, @RequestParam(required = false) String error) {
        if(!"true".equals(error) || error == null) {
            model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
            model.addAttribute("loginUser", authenticationFacade.getLoginUser());
        }
        model.addAttribute("data", friendsRepository.selectFriendsList(authenticationFacade.getLoginUserPk()));
        model.addAttribute("msgCnt", messageRepository.beforeReadMsgCnt(authenticationFacade.getLoginUserPk()));
        model.addAttribute("userData", mainService.userRepository.findByIuser(authenticationFacade.getLoginUserPk()));

        return "main/main";
    }

    @PostMapping("/profile")
    public int profile(){
        return 0;
    }

    @GetMapping("/search")
    public String search(@RequestParam int type, String search, Model model,Pageable pageable) {
        if (type == 0) {
            model.addAttribute("searchProfile", mainService.search(search, pageable).getProfile());
            model.addAttribute("searchPhoto", mainService.search(search, pageable).getPhoto());
            model.addAttribute("searchDiary", mainService.search(search, pageable).getDiary());
        } else {
            model.addAttribute("searchUser", mainService.searchUsers(search));
        }
        System.out.println(mainService.search(search, pageable).getDiary());
        return "main/search";
    }
}
