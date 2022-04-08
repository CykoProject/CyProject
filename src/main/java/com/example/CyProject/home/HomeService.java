package com.example.CyProject.home;

import com.example.CyProject.common.MyFileUtils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.home.model.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final AuthenticationFacade auth;
    private final MyFileUtils myFileUtils;
    private final ProfileRepository profileRepository;

    public int writeProfile(MultipartFile img, ProfileEntity entity) {
        entity.setIhost(auth.getLoginUserPk());

        String target = "profile/" + entity.getIhost();
        String saveFileName = myFileUtils.transferTo(img, target);
        if (saveFileName != null) {
            entity.setImg(saveFileName);
        }
        profileRepository.save(entity);
        return 1;
    }
}
