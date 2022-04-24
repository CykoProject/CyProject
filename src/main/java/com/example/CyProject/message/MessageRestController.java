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
        for(MessageEntity item : list) {
            messageRepository.save(item);
        }
        return 0;
    }
}
