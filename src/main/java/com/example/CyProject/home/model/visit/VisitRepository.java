package com.example.CyProject.home.model.visit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Integer> {

    List<VisitEntity> findByIhostOrderByRdtDesc(int ihost);

    Page<VisitEntity> findAllByIhost(int ihost, Pageable pageable);

    int countAllByIhost(int ihost);
}