package com.example.CyProject.home.model.visitor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Integer> {

    @Query("select count(u) from VisitorEntity u where u.visitorPk.ihome = ?1 and u.visitorPk.rdt = CURRENT_DATE ")
    int todayCount(int ihome);



}
