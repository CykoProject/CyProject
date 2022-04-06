package com.example.CyProject.user;


import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserContorller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/join")
    public void join(){

    }

    @PostMapping("/join")
    public String joinProc(UserEntity entity){
        userRepository.save(entity);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public void login(){

    }
}
