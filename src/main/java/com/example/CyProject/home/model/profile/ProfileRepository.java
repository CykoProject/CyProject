package com.example.CyProject.home.model.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    ProfileEntity findTop1ByIhostOrderByRdtDesc(int iuser);

    @Query("select p from ProfileEntity as p where p.ctnt like CONCAT ('%',:search,'%')")
    public List<ProfileEntity> searchProfile(String search);
    }

