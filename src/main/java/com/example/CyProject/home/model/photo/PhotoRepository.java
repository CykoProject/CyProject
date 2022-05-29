package com.example.CyProject.home.model.photo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {
    List<PhotoEntity> findByIhostOrderByRdtDesc(int iuser);
}
