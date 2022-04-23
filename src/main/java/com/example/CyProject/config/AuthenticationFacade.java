package com.example.CyProject.config;

import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    @Autowired private UserRepository userRepository;

    public UserEntity getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        UserEntity data = userRepository.findByEmail(email);
        if(data == null) {
            return UserEntity.builder().iuser(0).build();
        }

        return data;
    }

    public int getLoginUserPk() {
        int loginUserPk = 0;
        try {
            loginUserPk = getLoginUser().getIuser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginUserPk;
    }

    public String loginChk(String url) {
        int pk = getLoginUserPk();
        if(pk > 0) {
            return url;
        }
        return "redirect:/";
    }
}
