package com.example.CyProject.message;

import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.MessageEntity;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.user.model.friends.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/msg")
public class MessageController {

    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private MessageRepository messageRepository;
    @Autowired private FriendsRepository friendsRepository;
    @Autowired private Utils utils;

    @GetMapping("/inbox")
    public String inBox(Model model) {
        int receiver = authenticationFacade.getLoginUserPk();
        model.addAttribute("msgList", messageRepository.getReceiveMsgList(receiver));
        model.addAttribute("box", "inbox");
        return authenticationFacade.loginChk("message/inbox");
    }

    @GetMapping("/outbox")
    public String outBox(Model model) {
        int iuser = authenticationFacade.getLoginUserPk();
        model.addAttribute("msgList", messageRepository.getSendMsgList(iuser));
        model.addAttribute("box", "outbox");
        return authenticationFacade.loginChk("message/outbox");
    }

    @GetMapping("/write")
    public String writeMsg(Model model) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        model.addAttribute("friendList", friendsRepository.selectFriendsList(loginUserPk));
        model.addAttribute("loginUserPk", authenticationFacade.getLoginUserPk());
        return authenticationFacade.loginChk("message/write");
    }

    @GetMapping("/detail")
    public String detail(int imsg, Model model) {
        int updChk = messageRepository.updRecvRead(imsg);
        int loginUserPk = authenticationFacade.getLoginUserPk();
        Optional msgOpt = messageRepository.findById(imsg);
        if(msgOpt.isEmpty()) {
            return "redirect:/";
        }
        Object msgObj = msgOpt.get();
        MessageEntity msgEnt = (MessageEntity) msgObj;

        if((msgEnt.getReceiver().getIuser() != loginUserPk) && (msgEnt.getIuser().getIuser() != loginUserPk)) {
            return "redirect:/";
        }

        Optional<MessageEntity> optEntity = messageRepository.findById(imsg);
        MessageEntity entity = optEntity.get();
        entity.setCtnt(entity.getCtnt().replaceAll("\n", "<br>"));

        model.addAttribute("msgDetail", entity);
        model.addAttribute("loginUserPk", loginUserPk);
        return authenticationFacade.loginChk("message/detail");
    }
}