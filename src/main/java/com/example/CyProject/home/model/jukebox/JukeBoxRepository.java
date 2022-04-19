package com.example.CyProject.home.model.jukebox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JukeBoxRepository extends JpaRepository<JukeBoxEntity, Integer> {

    List<JukeBoxEntity> findAllByIhost(int ihost);
}
