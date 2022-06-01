package com.example.CyProject.home.model.photo;

import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import java.util.List;
import java.util.Map;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {

    @Query("select p from PhotoEntity as p where p.title like CONCAT ('%',:search,'%')")
    List<PhotoEntity> searchPhoto(String search);

    PhotoEntity findByIphoto(int iphoto);

    List<PhotoEntity> findByIhostOrderByRdtDesc(UserEntity iuser);
    List<PhotoEntity> findTop4ByIhostAndRdtBetweenOrderByRdtDesc(UserEntity ihost, LocalDateTime start, LocalDateTime end);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PhotoEntity SET scrap = scrap + 1 WHERE iphoto = ?1")
    void plusScrap(int iphoto);
    List<PhotoEntity> findByIhostOrderByRdtDesc(int iuser);

    @Query(value = "select COUNT(p.iphoto) as cnt, p.ihost as iuser, u.email, u.nm, u.profile_img from home_photos AS p INNER JOIN `user` AS u ON p.ihost = u.iuser group BY p.ihost order by cnt DESC LIMIT 5", nativeQuery = true)
    List<PhotoInterface> countPhotoByUser();

//    List<PhotoEntity> findAllByIhost(UserEntity entity);
}
