package com.example.CyProject.user;

import com.example.CyProject.ResultVo;
import com.example.CyProject.config.MyUserDetailsService;
import com.example.CyProject.user.model.UserDto;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private UserDetailsService service;

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

    @GetMapping("/mypage")
    public String mypage(Model model, UserDto dto) {
        String userName = dto.getNm();
        model.addAttribute("user", userName);
        return "user/mypage";
    }

    @PostMapping("/mypage")
    public String mypageProc(UserDto dto) {
        return "redirect:/home?iuser=1";
    }

    @GetMapping("/idChk/{email}")
    @ResponseBody
    public ResultVo idChk(@PathVariable String email){
        ResultVo result = new ResultVo();
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        result.setResult(userRepository.findByEmail(entity.getEmail()) == null ? 0 : 1);
        return result;
    }
}
