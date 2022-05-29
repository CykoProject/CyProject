package com.example.CyProject.home.model.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    List<ProfileEntity> findByIhostOrderByRdtDesc(int iuser);
}
