package com.example.CyProject.home.model.photo;

import com.example.CyProject.home.model.profile.ProfileEntity;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import java.util.List;
import java.util.Map;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {

    @Query("select p from PhotoEntity as p where p.title like CONCAT ('%',:search,'%')")
    List<PhotoEntity> searchPhoto(String search);
    List<PhotoEntity> findByIhostOrderByRdtDesc(int iuser);

    @Query(value = "select count(iphoto) as cnt, ihost as ihost from home_photos group by ihost order by cnt desc", nativeQuery = true)
    List<PhotoInterface> countPhotoByUser();

//    List<PhotoEntity> findAllByIhost(UserEntity entity);
}
