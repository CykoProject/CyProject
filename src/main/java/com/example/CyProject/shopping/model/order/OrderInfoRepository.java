package com.example.CyProject.shopping.model.order;

import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OrderInfoRepository extends JpaRepository <OrderInfoEntity, Integer> {

    OrderInfoEntity findTopByIuserOrderByRdtDesc(UserEntity entity);

    @Modifying
    @Transactional
    @Query("DELETE from OrderInfoEntity where iuser = :iuser and complete = 0")
    int deleteOrderIncomplete(UserEntity iuser);

    @Query("select count(order_id) from OrderInfoEntity where complete = 0 and iuser = :iuser")
    Integer isIncompleteOrder(UserEntity iuser);

    @Modifying
    @Transactional
    @Query("update OrderInfoEntity set complete = 1 where complete = 0 and iuser = :iuser")
    int OrderComplete(UserEntity iuser);
}
