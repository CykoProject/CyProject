package com.example.CyProject.user.model.points;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistoryEntity, Integer> {

    PointHistoryEntity findByIuser(int iuser);

}
