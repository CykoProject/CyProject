package com.example.CyProject.shopping.model.order;

import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInfoRepository extends JpaRepository <OrderInfoEntity, Integer> {

    OrderInfoEntity findTopByIuserOrderByRdtDesc(UserEntity entity);
}
