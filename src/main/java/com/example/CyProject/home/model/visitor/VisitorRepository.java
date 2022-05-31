package com.example.CyProject.home.model.visitor;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Integer> {

    String bestVisitorSql = "SELECT iuser, COUNT(iuser) AS cnt" +
            " FROM home_visitor" +
            " GROUP BY iuser" +
            " ORDER BY cnt DESC" +
            " LIMIT 5";

    @Query("select count(u) from VisitorEntity u where u.visitorPk.ihome = ?1 and u.visitorPk.rdt = CURRENT_DATE ")
    int todayCount(int ihome);

    @Query(value = bestVisitorSql, nativeQuery = true)
    List<VisitorInterFace> getBestVisitor();
}
