package com.example.CyProject.home.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<HomeEntity, Integer> {

    HomeEntity findByIuser(int iuser);
}
