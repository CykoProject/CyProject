package com.example.CyProject.main;

import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.home.model.visitor.VisitorRepository;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.user.model.friends.FriendsRepository;
import com.example.CyProject.user.model.friends.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private MainService mainService;
    @Autowired private MessageRepository messageRepository;
    @Autowired private FriendsRepository friendsRepository;
    @Autowired private FriendsService friendsService;
    @Autowired private VisitorRepository visitorRepository;
    @Autowired private VisitRepository visitRepository;
    @Autowired private  Utils utils;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String main(Model model, @RequestParam(required = false) String error) {
        if(!"true".equals(error) || error == null) {
            model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
            model.addAttribute("loginUser", authenticationFacade.getLoginUser());
        }
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(0), LocalTime.of(0,0,0));
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(),LocalTime.of(23,59,59));

        if(utils.findHomePk(authenticationFacade.getLoginUserPk()) != 0){
            model.addAttribute("visit",visitRepository.countByRdtBetween(startDate,endDate));
            System.out.println("startDate" + startDate);
            System.out.println("endDate"+ endDate);
        }

        model.addAttribute("visitor", visitorRepository.todayCount(utils.findHomePk(authenticationFacade.getLoginUserPk())));
        model.addAttribute("friend", friendsService.selectFriendsList(authenticationFacade.getLoginUserPk()));
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

    @GetMapping("/point")
    public String point() {

        return "main/point";
    }
}
