package com.example.CyProject.home.model.diary;

import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.home.model.profile.ProfileEntity;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {

    @Query("SELECT d FROM DiaryEntity d WHERE d.ihost = ?1 AND (d.rdt >= ?2 AND d.rdt < ?3)")
    Page<DiaryEntity> findAllByIhostAndRdt(int ihost, LocalDateTime startDate, LocalDateTime lastDate, Pageable pageable);
    Page<DiaryEntity> findAllByIhost(int ihost, Pageable pageable);

    @Query("SELECT COUNT(d) FROM DiaryEntity d WHERE d.ihost = ?1 AND (d.rdt >= ?2 AND d.rdt < ?3)")
    int countAllByIhostAndRdt(int ihost, LocalDateTime startDate, LocalDateTime tomorrow);
    int countAllByIhost(int ihost);

    @Query("select d from DiaryEntity as d where d.ctnt like CONCAT ('%',:search,'%')")
    List<DiaryEntity> searchDiary(String search);

    @Query("select count(d.idiary), d.ihost from DiaryEntity d group by d.ihost")
    List<DiaryEntity> countDiaryByUser();
}
