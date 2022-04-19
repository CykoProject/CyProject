package com.example.CyProject.home;

import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.home.model.profile.PhotoImgEntity;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.CyProject.common.MyFileUtils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.home.model.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class HomeService {

    private final AuthenticationFacade auth;
    private final MyFileUtils myFileUtils;
    private final ProfileRepository profileRepository;

    public int writeProfile(MultipartFile profileImg, ProfileEntity entity) {
        // TODO : profile 사진 없이 글 썼을 때 체크

        entity.setIhost(auth.getLoginUserPk());

        String target = "profile/" + entity.getIhost();

        String saveFileName = myFileUtils.transferTo(profileImg, target);

        if (saveFileName != null) {
            entity.setImg(saveFileName);
        }
        profileRepository.save(entity);

        return 1;
    }

    public int writePhoto(MultipartFile[] imgs, PhotoEntity entity) {
        // TODO : database photos, home_photos 테이블 수정
        if (imgs == null) {
            return 0;
        }

        entity.setIhost(auth.getLoginUserPk());
        //photoRepository.save(entity);

        String target = "photos/" + entity.getIphoto();
        PhotoImgEntity imgEntity = new PhotoImgEntity();
        imgEntity.setIphoto(entity.getIphoto());

        for (MultipartFile img : imgs) {
            String saveFileName = myFileUtils.transferTo(img, target);

            imgEntity.setImg(saveFileName);
            //photoImgRepository.save(entity);
        }

        return 1;
    }
}
