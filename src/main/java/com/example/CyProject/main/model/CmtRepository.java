package com.example.CyProject.main.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmtRepository extends JpaRepository<CmtEntity, Integer> {

    List<CmtEntity> findAll();
}
