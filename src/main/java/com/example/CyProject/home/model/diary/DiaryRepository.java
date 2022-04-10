package com.example.CyProject.home.model.diary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {

    List<DiaryEntity> findByIhostOrderByRdtDesc(int ihost);
}
