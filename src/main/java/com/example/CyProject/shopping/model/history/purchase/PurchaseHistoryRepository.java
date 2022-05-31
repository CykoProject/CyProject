package com.example.CyProject.shopping.model.history.purchase;

import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistoryEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update PurchaseHistoryEntity set complete = 1 where complete = 0 and iuser = :iuser")
    int purchaseComplete(UserEntity iuser);

    @Query("select count(ihistory) from PurchaseHistoryEntity where complete = 0 and iuser = :iuser")
    Integer isIncompletePurchase(UserEntity iuser);

    @Query("select distinct item_id from PurchaseHistoryEntity where complete = 0 and iuser = :iuser")
    List<ItemEntity> purchaseItemIdList(UserEntity iuser);

    @Modifying
    @Transactional
    @Query("DELETE from PurchaseHistoryEntity where iuser = :iuser and complete = 0")
    int deletePurchaseFailData(UserEntity iuser);

    @Query("SELECT p FROM PurchaseHistoryEntity p WHERE (p.item_id.icategory = :icategory AND p.iuser.iuser = :iuser)")
    List<PurchaseHistoryEntity> findAllByIcategoryInHisotry(int icategory, int iuser);

    @Query("select item_id from PurchaseHistoryEntity where complete = 1 and iuser = :iuser")
    List<ItemEntity> purchaseItemList(UserEntity iuser);
}
