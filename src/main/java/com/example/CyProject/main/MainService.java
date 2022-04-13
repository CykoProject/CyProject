package com.example.CyProject.main;

import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    @Autowired
    UserRepository userRepository;

    public List<UserEntity> searchUsers(String search) {

        return userRepository.findByNm(search);
    }

}
