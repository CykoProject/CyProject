package com.example.CyProject.home.model.visitor;


import com.example.CyProject.main.model.top.TopHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Integer> {

    String bestVisitorSql = "SELECT count(A.ihome) AS cnt, B.nm, B.email, B.iuser, B.profile_img AS img" +
            " FROM home A" +
            " left JOIN `user` B" +
            " ON A.iuser = B.iuser" +
            " INNER JOIN home_visitor C" +
            " ON C.ihome = A.ihome" +
            " GROUP BY B.iuser" +
            " ORDER BY cnt DESC" +
            " LIMIT 5";

    @Query("select count(u) from VisitorEntity u where u.visitorPk.ihome = ?1 and u.visitorPk.rdt = CURRENT_DATE ")
    int todayCount(int ihome);

    @Query(value = bestVisitorSql, nativeQuery = true)
    List<TopHelper> getBestVisitor();
}
