package com.example.CyProject.home.model.home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface HomeRepository extends JpaRepository<HomeEntity, Integer> {

    HomeEntity findByIuser(int iuser);

    @Modifying
    @Transactional
    @Query("UPDATE HomeEntity SET home_nm = ?1 WHERE ihome = ?2")
    int updHomeNm(String home_nm, int ihome);

    @Modifying
    @Transactional
    @Query(value = "UPDATE HomeEntity h set h.scope = ?1 WHERE h.iuser = ?2")
    int updateScope(int scope, int iuser);
}
