package com.example.CyProject.main.model;

import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmtRepository extends JpaRepository<CmtEntity, Integer> {

    List<CmtEntity> findAllByOrderByRdtDesc(Pageable pageable);

    @Query("select count(icmt) from CmtEntity")
    Integer countCmt();
}
