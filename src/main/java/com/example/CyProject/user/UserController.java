package com.example.CyProject.user;

import com.example.CyProject.user.model.UserDto;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {

        return "user/login";
    }

    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    @PostMapping("/join")
    public String joinProc(UserDto dto) {
        dto.setUpw(passwordEncoder.encode(dto.getUpw()));
        userRepository.save(dto.toEntity());
        return "redirect:/user/login";
    }
}
