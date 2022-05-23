package com.example.CyProject.shopping.model.history.purchase;

import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistoryEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update PurchaseHistoryEntity set complete = 1 where complete = 0 and iuser = :iuser")
    int purchaseComplete(UserEntity iuser);

    @Query("select count(ihistory) from PurchaseHistoryEntity where complete = 0 and iuser = :iuser")
    Integer isIncompletePurchase(UserEntity iuser);

    @Modifying
    @Transactional
    @Query("DELETE from PurchaseHistoryEntity where iuser = :iuser and complete = 0")
    int deletePurchaseFailData(UserEntity iuser);
}
