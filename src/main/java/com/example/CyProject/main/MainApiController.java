package com.example.CyProject.main;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.main.model.CmtEntity;
import com.example.CyProject.main.model.CmtRepository;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class MainApiController {

    @Autowired
    CmtRepository cmtRepository;
    @Autowired
    AuthenticationFacade authenticationFacade;

    @PostMapping("/cmt/add")
    public int addCmt(@RequestBody String ctnt) {
        UserEntity userEntity = new UserEntity();
        userEntity.setIuser(authenticationFacade.getLoginUserPk());

        CmtEntity entity = new CmtEntity();
        entity.setIuser(userEntity);
        entity.setCtnt(ctnt);

        cmtRepository.save(entity);

        return 0;
    }

    @GetMapping("/cmt")
    public List<CmtEntity> callCmtList(@PageableDefault Pageable pageable) {
        return cmtRepository.findAllByOrderByRdtDesc(pageable);
    }

    @GetMapping("/cmt/count")
    public int callCmtCount() {
        return cmtRepository.countCmt();
    }
}
