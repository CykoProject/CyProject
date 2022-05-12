package com.example.CyProject.user;

import com.example.CyProject.common.MyFileUtils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService{

    @Autowired MyFileUtils fileUtils;
    @Autowired AuthenticationFacade auth;
    @Autowired UserRepository userRepository;

    public String uploadProfileImg(MultipartFile multipartFile) {
        if(multipartFile == null) { return null; }

        UserEntity loginUser = auth.getLoginUser();
        String path = "C:\\img/user/" + loginUser.getIuser();
        String fileNm = fileUtils.saveFile(path, multipartFile);
        System.out.println("fileNm : " + fileNm);
        if(fileNm == null) { return null; }

        //기존 파일명

        String oldFilePath = path + "/" + loginUser.getImg();
        fileUtils.delFile(oldFilePath);

        //파일명을 user 테이블에 update
        Optional<UserEntity> user = userRepository.findById(auth.getLoginUserPk());
        user.ifPresent(selectUser -> {
            selectUser.setImg(fileNm);
            userRepository.save(selectUser);
        });
        return fileNm;
    }
}
