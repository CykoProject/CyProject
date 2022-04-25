package com.example.CyProject.message;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.MessageEntity;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.user.model.UserRepository;
import com.example.CyProject.user.model.friends.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/msg")
public class MessageController {

    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private MessageRepository messageRepository;
    @Autowired private FriendsRepository friendsRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("")
    public String mailBox(Model model) {
        int receiver = authenticationFacade.getLoginUserPk();

        model.addAttribute("msgList", messageRepository.getMsgList(receiver));
        return authenticationFacade.loginChk("message/mailbox");
    }

    @GetMapping("/write")
    public String writeMsg(Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        model.addAttribute("friendList", friendsRepository.selectFriendsList(loginUserPk));
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        return authenticationFacade.loginChk("message/write");
    }

    @GetMapping("/detail")
    public String detail(int imsg) {
        int updChk = messageRepository.updRecvRead(imsg);
        return authenticationFacade.loginChk("message/detail");
    }
}
