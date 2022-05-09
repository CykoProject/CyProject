package com.example.CyProject.message;

import com.example.CyProject.PageEntity;
import com.example.CyProject.Utils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.MessageEntity;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.message.model.MessageSaveBoxEntity;
import com.example.CyProject.message.model.MessageSaveBoxRepository;
import com.example.CyProject.user.model.friends.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/msg")
public class MessageController {

    @Autowired private MessageSaveBoxRepository messageSaveBoxRepository;
    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private MessageRepository messageRepository;
    @Autowired private FriendsRepository friendsRepository;
    @Autowired private MsgPageService pageService;
    @Autowired private Utils utils;

    @GetMapping("/inbox")
    public String inBox(Model model,
                        @RequestParam(required = false, defaultValue = "1", value = "page")int page) {
        int receiver = authenticationFacade.getLoginUserPk();
        int rowCnt = 10;
        int pageCnt = 10;

        Page<MessageEntity> list = pageService.msgInBoxPaging(receiver, page, rowCnt);

        PageEntity pageEntity = new PageEntity.Builder()
                .page(page)
                .pageCnt(pageCnt)
                .maxPage(list.getTotalPages())
                .rowCnt(rowCnt)
                .build();

        for(MessageEntity item : list) {
            int ctntCnt = item.getCtnt().length();
            if(ctntCnt > 50) {
                item.setCtnt(item.getCtnt().substring(0, 49) + "...");
            }
        }
        model.addAttribute("msgList", list);
        model.addAttribute("box", "inbox");
        model.addAttribute("pageData", pageEntity);
        return authenticationFacade.loginChk("message/inbox");
    }

    @GetMapping("/outbox")
    public String outBox(Model model,
                         @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        int iuser = authenticationFacade.getLoginUserPk();
        int rowCnt = 10;
        int pageCnt = 10;

        Page<MessageEntity> list = pageService.msgOutBoxPaging(iuser, page, rowCnt);

        PageEntity pageEntity = new PageEntity.Builder()
                .page(page)
                .pageCnt(pageCnt)
                .maxPage(list.getTotalPages())
                .rowCnt(rowCnt)
                .build();

        for(MessageEntity item : list) {
            int ctntCnt = item.getCtnt().length();
            if(ctntCnt > 50) {
                item.setCtnt(item.getCtnt().substring(0, 40) + "...");
            }
        }
        model.addAttribute("msgList", list);
        model.addAttribute("box", "outbox");
        model.addAttribute("pageData", pageEntity);
        return authenticationFacade.loginChk("message/outbox");
    }

    @GetMapping("/savebox")
    public String saveBox(Model model,
                          @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        int loginUserPk = authenticationFacade.getLoginUserPk();
        int rowCnt = 10;
        int pageCnt = 10;

        Page<MessageSaveBoxEntity> list = pageService.msgSaveBoxPaging(loginUserPk, page, rowCnt);

        PageEntity pageEntity = new PageEntity.Builder()
                .page(page)
                .pageCnt(pageCnt)
                .maxPage(list.getTotalPages())
                .rowCnt(rowCnt)
                .build();

        for(MessageSaveBoxEntity item : list) {
            int ctntCnt = item.getImsg().getCtnt().length();
            if(ctntCnt > 50) {
                item.getImsg().setCtnt(item.getImsg().getCtnt().substring(0, 40) + "...");
            }
        }

        model.addAttribute("msgList", list);
        model.addAttribute("box", "savebox");
        model.addAttribute("pageData", pageEntity);
        return authenticationFacade.loginChk("message/savebox");
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