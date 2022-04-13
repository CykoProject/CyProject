package com.example.CyProject.config;

import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByEmail(email);
        if(entity == null) throw new UsernameNotFoundException("해당 사용자가 존재하지 않습니다.");

        session.setAttribute("loginUserPk", entity.getIuser());

        return new MyUserDetails(entity);
    }
}
