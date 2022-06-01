package com.example.CyProject.home.model.scrap;

import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardListRepository extends JpaRepository<BoardListEntity, BoardListId> {

    List<BoardListEntity> findAllByIuserOrderByIphotoDesc(UserEntity iuser);

    @Transactional
    void deleteByIphotoAndIuser(PhotoEntity iphoto, UserEntity iuser);

    @Query("SELECT b FROM BoardListEntity b WHERE b.iuser = ?1 AND b.iphoto.rdt BETWEEN ?2 AND ?3 ORDER BY b.iphoto.rdt DESC")
    List<BoardListEntity> newPhotos(UserEntity ihost, LocalDateTime start, LocalDateTime end);

    BoardListEntity findByIphotoAndIuser(PhotoEntity iphoto, UserEntity iuser);
}
