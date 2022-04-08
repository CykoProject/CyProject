package com.example.CyProject.home;

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

    @Autowired private VisitRepository visitRepository;

    public Page<VisitEntity> visitPaging(int ihost, int page) {
        return visitRepository.findAllByIhost(ihost, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "rdt")));
    }

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
