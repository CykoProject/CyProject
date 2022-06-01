package com.example.CyProject.main;

import com.example.CyProject.ResultVo;
import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.photo.PhotoInterface;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.home.model.visitor.VisitorRepository;
import com.example.CyProject.home.model.visitor.VisitorService;
import com.example.CyProject.main.model.CmtRepository;
import com.example.CyProject.main.model.top.TopHelper;
import com.example.CyProject.main.model.top.TopService;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryRepository;
import com.example.CyProject.shopping.model.item.ItemCategory;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import com.example.CyProject.user.model.friends.FriendsEntity;
import com.example.CyProject.user.model.friends.FriendsRepository;
import com.example.CyProject.user.model.friends.FriendsService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private MainService mainService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private VisitorRepository visitorRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private CmtRepository cmtRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private TopService topService;
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired private PurchaseHistoryRepository purchaseHistoryRepository;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String main(Model model, @RequestParam(required = false) String error, @PageableDefault(size = 10) Pageable pageable) {
        if (!"true".equals(error) || error == null) {
            model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
            model.addAttribute("loginUser", authenticationFacade.getLoginUser());
        }

        if (utils.findHomePk(authenticationFacade.getLoginUserPk()) != 0) {
            LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(0), LocalTime.of(0, 0, 0));
            LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
            model.addAttribute("visit", visitRepository.countByIhostAndRdtBetween(authenticationFacade.getLoginUserPk(), startDate, endDate));
        }

        model.addAttribute("bestFriends", topService.toTopFiveList(friendsRepository.getBestFriends()));
        model.addAttribute("bestVisitor", topService.toTopFiveList(visitorRepository.getBestVisitor()));
        model.addAttribute("cmt", cmtRepository.findAllByOrderByRdtDesc(pageable));
        model.addAttribute("visitor", visitorRepository.todayCount(utils.findHomePk(authenticationFacade.getLoginUserPk())));
        model.addAttribute("friend", friendsService.selectFriendsList(authenticationFacade.getLoginUserPk()));
        model.addAttribute("data", friendsRepository.selectFriendsList(authenticationFacade.getLoginUserPk()));
        model.addAttribute("msgCnt", messageRepository.beforeReadMsgCnt(authenticationFacade.getLoginUserPk()));
        model.addAttribute("userData", mainService.userRepository.findByIuser(authenticationFacade.getLoginUserPk()));
        model.addAttribute("photoTop", mainService.photoTop());
        model.addAttribute("diaryTop", mainService.diaryTop());
        model.addAttribute("sumTop", mainService.sumTop());

        return "main/main";
    }

    @PostMapping("/profile")
    public int profile() {
        return 0;
    }

    @GetMapping("/search")
    public String search(@RequestParam int type, String search, Model model, @PageableDefault(size = 5) Pageable pageable) {
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

    @GetMapping("/friendfind")
    public String friendfind(Model model, @RequestParam(required = false) String search){
        if(search != null) {
            List<UserEntity> findSearch = userRepository.findByEmailOrNmOrCellphoneContaining(search, search ,search);

            for(int i=0; i<findSearch.size(); i++) {
                UserEntity item = findSearch.get(i);
                if(item.getIuser() == authenticationFacade.getLoginUserPk()) {
                    findSearch.remove(i);
                    --i;
                } else {
                    String cellPhone = item.getCellphone();
                    String regex = FriendsService.convertTelNo(cellPhone);
                    item.setCellphone(regex);
                }
            }

            model.addAttribute("select",findSearch);
        }
        List<FriendsEntity> selectFuser = friendsRepository.selecSenderList(authenticationFacade.getLoginUserPk());
        List<UserEntity> senderData = new ArrayList<>();
        for(FriendsEntity item : selectFuser) {
            senderData.add(friendsService.getUserData(item.getIuser()));
        }

        List<UserEntity> receiverData = new ArrayList<>();
        List<FriendsEntity> receiverList = friendsRepository.selectfuserFriends(authenticationFacade.getLoginUserPk());
        for(FriendsEntity item : receiverList) {
            receiverData.add(friendsService.getUserData(item.getIuser()));
        }
        model.addAttribute("receiver", receiverData);
        model.addAttribute("selectfuser",senderData);
        System.out.println(selectFuser);
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        return "main/friendfind";
    }

    @PostMapping("/friendfind")
    public String findselect(String search, int category) throws Exception{
        search = URLEncoder.encode(search, "UTF-8");
        return "redirect:/friendfind?search=" + search + "&category=" + category;
    }

    @ResponseBody
    @GetMapping("/accept/friend")
    public ResultVo acceptFriend(@RequestParam int receiver, @RequestParam String nickname) {
        int iuser = authenticationFacade.getLoginUserPk();
        ResultVo vo = new ResultVo();
        vo.setResult(1);

        FriendsEntity sendData =  friendsRepository.hasFriendsByIuserAndFuser(receiver, iuser);
        sendData.setStatus(1);
        friendsRepository.save(sendData);

        if(friendsService.isFriend(iuser, receiver)) {
            FriendsEntity receiverData = new FriendsEntity();
            UserEntity user = new UserEntity();
            user.setIuser(receiver);
            receiverData.setStatus(1);
            receiverData.setIuser(iuser);
            receiverData.setFuser(user);
            receiverData.setNickname(nickname);
            System.out.println(receiverData);
            friendsRepository.save(receiverData);
        }

        return vo;
    }
}
