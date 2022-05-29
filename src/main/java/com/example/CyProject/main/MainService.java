package com.example.CyProject.main;

import com.example.CyProject.home.model.diary.DiaryRepository;
import com.example.CyProject.home.model.photo.PhotoRepository;
import com.example.CyProject.home.model.profile.ProfileRepository;
import com.example.CyProject.main.model.SearchEntity;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    public List<UserEntity> searchUsers(String search, Pageable pageable) {
        return userRepository.findByNmContains(search, pageable);
    }

    public int searchUsersCount(String search) {
        return userRepository.findByNmContains(search).size();
    }

    public SearchEntity search(String search) {

        SearchEntity entity = new SearchEntity();

        entity.setProfile(profileRepository.searchProfile(search));
        entity.setPhoto(photoRepository.searchPhoto(search));
        entity.setDiary(diaryRepository.searchDiary(search));

        return entity;
    }
}
