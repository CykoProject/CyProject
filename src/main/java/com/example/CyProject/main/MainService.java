package com.example.CyProject.main;

import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.home.model.profile.ProfileRepository;
import com.example.CyProject.main.model.SearchEntity;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    ProfileRepository profileRepository;

    public List<UserEntity> searchUsers(String search) {
        return userRepository.findByNm(search);
    }

    public SearchEntity search(String search, Pageable pageable) {

        SearchEntity entity = new SearchEntity();

        entity.setProfile(profileRepository.searchProfile(search, pageable));
        entity.setPhoto(photoRepository.searchPhoto(search, pageable));
        entity.setDiary(diaryRepository.searchDiary(search, pageable));

        return entity;
    }

}
