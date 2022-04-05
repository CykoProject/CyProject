package com.example.CyProject.home.model.profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

//    ProfileEntity findByIhost(int ihost);
}
