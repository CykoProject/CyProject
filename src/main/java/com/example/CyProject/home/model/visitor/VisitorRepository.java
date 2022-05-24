package com.example.CyProject.home.model.visitor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Integer> {

    @Query("select u from VisitorEntity u where u.visitorPk.ihome = ?1")
    String todayCount(int ihome);
}
