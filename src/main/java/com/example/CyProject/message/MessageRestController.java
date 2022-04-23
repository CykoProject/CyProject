package com.example.CyProject.message;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.message.model.MessageSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ajax/msg")
public class MessageRestController {

    @Autowired private AuthenticationFacade authenticationFacade;

    @PostMapping("/write")
    public int msgWriteProc(@RequestBody MessageSave save) {
        System.out.println(save.toEntity(authenticationFacade.getLoginUserPk()));
        return 0;
    }
}
