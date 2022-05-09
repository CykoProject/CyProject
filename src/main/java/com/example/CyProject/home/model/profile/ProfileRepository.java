package com.example.CyProject.home.model.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    ProfileEntity findTop1ByIhostOrderByRdtDesc(int iuser);
}
