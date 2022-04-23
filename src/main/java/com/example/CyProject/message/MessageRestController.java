package com.example.CyProject.message;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.MessageEntity;
import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.message.model.MessageSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajax/msg")
public class MessageRestController {

    @Autowired private AuthenticationFacade authenticationFacade;
    @Autowired private MessageRepository messageRepository;

    @PostMapping("/write")
    public int msgWriteProc(@RequestBody MessageSave save) {
        List<MessageEntity> list = save.toListEntity(authenticationFacade.getLoginUserPk());

        int cnt = 0;
        int result = 0;
        for(MessageEntity item : list) {
            MessageEntity me = messageRepository.save(item);
            if(me != null) { cnt++; }
        }
        if(cnt == list.size()) { ++result; }
        return result;
    }
}
