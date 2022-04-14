

package com.example.CyProject.user;

import com.example.CyProject.ResultVo;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.user.model.UserDto;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private UserService service;
    @Autowired private HomeRepository homeRepository;
    @Autowired private AuthenticationFacade auth;

    @RequestMapping(value = {"/login"},method = {RequestMethod.GET, RequestMethod.POST})
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
        int iuser = userRepository.save(dto.toEntity()).getIuser();
        HomeEntity entity = new HomeEntity();
        entity.setIuser(iuser);
        if(iuser != 0){
            homeRepository.save(entity);
        }
        return "redirect:/user/login";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        model.addAttribute("loginUser", auth.getLoginUser());
        return "user/mypage";
    }

    @PostMapping("/mypage")
    public String update(String newUpw) {
        String secureUpw = passwordEncoder.encode(newUpw);
        Optional<UserEntity> user = userRepository.findById(auth.getLoginUserPk());
        user.ifPresent(selectUser -> {
            selectUser.setUpw(secureUpw);
            userRepository.save(selectUser);
        });
        return "redirect:/";
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

    @GetMapping("/pwChk/{oldUpw}")
    @ResponseBody
    public ResultVo pwChk(@PathVariable String oldUpw) {
        ResultVo vo = new ResultVo();
        String upw = auth.getLoginUser().getUpw();
        vo.setResult(passwordEncoder.matches(oldUpw, upw) ? 1 : 0);
        return vo;
    }
}
