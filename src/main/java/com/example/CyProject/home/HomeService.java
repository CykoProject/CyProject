package com.example.CyProject.home;

import com.example.CyProject.home.model.friends.FriendsEntity;
import com.example.CyProject.home.model.friends.FriendsMapper;
import com.example.CyProject.home.model.home.HomeMessageEntity;
import com.example.CyProject.home.model.home.HomeMessageMapper;
import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.home.model.photo.PhotoImgEntity;
import com.example.CyProject.home.model.photo.PhotoImgRepository;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.visit.VisitEntity;
import com.example.CyProject.home.model.visit.VisitRepository;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.CyProject.common.MyFileUtils;
import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.home.model.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class HomeService {

    private final AuthenticationFacade auth;
    private final MyFileUtils myFileUtils;
    private final ProfileRepository profileRepository;
    private final PhotoImgRepository photoImgRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Autowired private FriendsMapper friendsMapper;
    @Autowired private HomeMessageMapper homeMessageMapper;
    @Autowired private VisitRepository visitRepository;

    public Page<VisitEntity> visitPaging(int ihost, int page) {
        return visitRepository.findAllByIhost(ihost, PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "rdt")));
    }

    public int writeProfile(MultipartFile profileImg, ProfileEntity entity) {
        // TODO : profile 사진 없이 글 썼을 때 체크

        UserEntity loginUser = auth.getLoginUser();
        entity.setIhost(loginUser.getIuser());
//        entity.setIhost(auth.getLoginUserPk());

        String target = "profile/" + entity.getIhost();

        String saveFileName = myFileUtils.transferTo(profileImg, target);

        if (saveFileName != null) {
            entity.setImg(saveFileName);
        }
        profileRepository.save(entity);

        // user 테이블에 프로필 추가
        // TODO : user 테이블 수정
        Optional<UserEntity> userOptional = userRepository.findById(loginUser.getIuser());

        userOptional.ifPresent(user -> {
            user.setProfile_img(saveFileName);
            user.setProfile_ctnt(entity.getCtnt());
            userRepository.save(user);
        });

        return 1;
    }

//    public int writePhoto(MultipartFile[] imgs, PhotoEntity entity) {
//        // TODO : database photos, home_photos 테이블 수정
//        if (imgs == null) {
//            return 0;
//        }
//
//        entity.setIhost(auth.getLoginUserPk());
//        photoRepository.save(entity);
//
//        String target = "photos/" + entity.getIphoto();
//        PhotoImgEntity imgEntity = new PhotoImgEntity();
//        imgEntity.setIphoto(entity.getIphoto());
//
//        for (MultipartFile img : imgs) {
//            String saveFileName = myFileUtils.transferTo(img, target);
//
//            imgEntity.setImg(saveFileName);
//            photoImgRepository.save(imgEntity);
//        }
//
//        return 1;
//    }

    public int writePhoto(MultipartFile imgs, PhotoImgEntity imgEntity) {
        // TODO : database photos, home_photos 테이블 수정
        if (imgs == null) {
            return 0;
        }

//        entity.setIhost(auth.getLoginUserPk());
//        photoRepository.save(entity);

        String target = "photos/" + imgEntity.getIphoto();


        String saveFileName = myFileUtils.transferTo(imgs, target);

        imgEntity.setImg(saveFileName);
        System.out.println(imgEntity);
        photoImgRepository.save(imgEntity);


        return 1;
    }

    public boolean insPhoto(PhotoEntity entity) {
        entity.setIhost(auth.getLoginUserPk());
        PhotoEntity result = photoRepository.save(entity);

        return result.getIhost() != 0;
    }

    public boolean insPhoto(PhotoEntity entity, List<MultipartFile> files) {
        boolean queryResult = false;
        if (!this.insPhoto(entity)) {
            return false;
        } else {
            List<PhotoImgEntity> fileList = this.myFileUtils.uploadFiles(files, entity.getIphoto());
            for (PhotoImgEntity file : fileList) {
//                if (!CollectionUtils.isEmpty(file)) {
//                    queryResult = photoImgRepository.save(file);
//                    if (queryResult < 1) {
//                        queryResult = 0;
//                    }
//                }
                photoImgRepository.save(file);
                queryResult = true;
            }
        }

        return queryResult;
    }

    public int selFriends(FriendsEntity entity) {
        return friendsMapper.selFriends(entity);
    }

    public int selMessage(HomeMessageEntity entity) {
        return homeMessageMapper.selMessage(entity);
    }

    public int updMessage(HomeMessageEntity entity) {
        return homeMessageMapper.updMessage(entity);
    }

    public List<HomeMessageEntity> selMessageList(int ihost) {
        return homeMessageMapper.selMessageList(ihost);
    }
}
