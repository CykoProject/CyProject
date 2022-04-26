package com.example.CyProject.mail;

import com.example.CyProject.ResultVo;
import com.example.CyProject.mail.model.EmailEntity;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private EmailService service;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/email")
    public ResultVo find_upw_sendEmail(@RequestBody EmailEntity email){
        ResultVo vo = new ResultVo();
        vo.setResultString(service.find_upw_sendEmail(email.getEmail()));
        System.out.println(vo);
        return vo;
    }

}
