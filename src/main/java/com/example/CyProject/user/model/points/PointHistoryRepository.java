package com.example.CyProject.user.model.points;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistoryEntity, Integer> {

    List<PointHistoryEntity> findByIuserOrderByRdtDesc(int iuser);
}
