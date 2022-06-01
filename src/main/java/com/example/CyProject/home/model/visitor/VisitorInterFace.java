package com.example.CyProject.home.model.visitor;

import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface VisitorInterFace {

    int getIuser();
    int getCnt();
}
