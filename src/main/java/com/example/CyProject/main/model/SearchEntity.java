package com.example.CyProject.main.model;

import com.example.CyProject.home.model.diary.DiaryEntity;
import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.home.model.photo.PhotoImgEntity;
import com.example.CyProject.home.model.profile.ProfileEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

@Data
public class SearchEntity {

    List<ProfileEntity> profile;

    List<DiaryEntity> diary;

    List<PhotoEntity> photo;
}
