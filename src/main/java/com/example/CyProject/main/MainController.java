package com.example.CyProject.main;

import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.user.model.friends.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String search(@RequestParam int type, String search, Model model, @PageableDefault(size=5) Pageable pageable) {
        if (type == 0) {
            model.addAttribute("searchProfile", mainService.search(search).getProfile());
            model.addAttribute("searchPhoto", mainService.search(search).getPhoto());
            model.addAttribute("searchDiary", mainService.search(search).getDiary());
            return "main/search";
        } else {
            model.addAttribute("searchUser", mainService.searchUsers(search, pageable));
            model.addAttribute("searchUserCount", mainService.searchUsersCount(search));
            System.out.println(mainService.searchUsers(search, pageable));
            System.out.println(mainService.searchUsersCount(search));
            return "main/searchUser";
        }
    }
}
