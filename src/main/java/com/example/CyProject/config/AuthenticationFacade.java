package com.example.CyProject.config;

import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.home.model.home.HomeRepository;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import com.example.CyProject.user.model.friends.FriendsEntity;
import com.example.CyProject.user.model.friends.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class AuthenticationFacade {

    @Autowired private UserRepository userRepository;
    @Autowired private HomeRepository homeRepository;
    @Autowired private FriendsRepository friendsRepository;

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

    public int isHomeVisitScope(int iuser) { // 미니홈피주인pk
        HomeEntity h = homeRepository.findByIuser(iuser);
        int scope = h.getScope();

        int isAccess = 0;

        switch (scope) {
            case 0 : // 비공개
                isAccess = getLoginUserPk() == iuser ? 2 : 0;
                break;
            case 1 : // 일촌공개
                List<Integer> f = friendsRepository.selectFriendsPkList(iuser);
                isAccess = iuser == getLoginUserPk() ? 2 : 1;
                for(Integer item : f) {
                    if(item == getLoginUserPk()) {
                        isAccess = 2;
                        break;
                    }
                }
                break;
            case 2 : // 전체공개
                isAccess = 2;
                break;
        }

        return isAccess;
    }

    public String returnPath(int iuser, Model model) {
        int isAccess = isHomeVisitScope(iuser);
        String err = isAccess == 0 ? "비공개 홈피입니다." : "일촌공개 홈피입니다.";
        model.addAttribute("err", err);

        return "home/error";
    }
}
