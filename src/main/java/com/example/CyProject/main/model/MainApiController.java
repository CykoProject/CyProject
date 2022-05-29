package com.example.CyProject.main.model;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cmt")
public class MainApiController {

    @Autowired
    CmtRepository cmtRepository;
    @Autowired
    AuthenticationFacade authenticationFacade;


    @PostMapping("/add")
    public int addCmt(@RequestBody String ctnt) {
        UserEntity userEntity = new UserEntity();
        userEntity.setIuser(authenticationFacade.getLoginUserPk());

        CmtEntity entity = new CmtEntity();
        entity.setIuser(userEntity);
        entity.setCtnt(ctnt);

        cmtRepository.save(entity);

        return 0;
    }
}
