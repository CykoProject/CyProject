package com.example.CyProject.home.model.photo;

import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {

    @Query("select p from PhotoEntity as p where p.title like CONCAT ('%',:search,'%')")
    List<PhotoEntity> searchPhoto(String search);
    List<PhotoEntity> findByIhostOrderByRdtDesc(UserEntity iuser);
    List<PhotoEntity> findTop4ByIhostAndRdtBetweenOrderByRdtDesc(UserEntity ihost, LocalDateTime start, LocalDateTime end);
}
