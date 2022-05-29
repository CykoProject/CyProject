package com.example.CyProject.home.model.photo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoImgRepository extends JpaRepository<PhotoImgEntity, Integer> {
    List<PhotoImgEntity> findAllByIphoto(int iphoto);
}
