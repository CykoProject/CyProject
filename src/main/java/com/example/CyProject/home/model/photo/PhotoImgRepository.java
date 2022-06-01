package com.example.CyProject.home.model.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PhotoImgRepository extends JpaRepository<PhotoImgEntity, Integer> {
    List<PhotoImgEntity> findAllByIphoto(int iphoto);
    PhotoImgEntity findByIphoto(int iphoto);

    @Transactional
    void deleteAllByIphoto(int iphoto);
}
