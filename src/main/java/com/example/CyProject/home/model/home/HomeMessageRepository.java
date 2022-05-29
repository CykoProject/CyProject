package com.example.CyProject.home.model.home;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeMessageRepository extends JpaRepository<HomeMessageEntity, Integer> {
    List<HomeMessageEntity> findByIhostOrderByRdtDesc(int ihost);
}
