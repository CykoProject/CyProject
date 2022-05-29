package com.example.CyProject.shopping.model.cart;

import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.mail.FetchProfile;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

    List<CartEntity> findAllByIuser(UserEntity iuser);

    CartEntity findByIuserAndItemid(UserEntity iuser, ItemEntity item_id);

    int countAllByIuser(int iuser);

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity set cnt = cnt + 1 WHERE iuser = :u AND itemid = :i")
    int updCartCnt(UserEntity u, ItemEntity i);

    @Modifying
    @Transactional
    @Query("update CartEntity set cnt = cnt - 1 where iuser = :u and itemid = :i")
    int subtractCartCnt(UserEntity u, ItemEntity i);

    @Query("SELECT sum(c.itemid.price * c.cnt) from CartEntity c where c.iuser = :iuser")
    Integer totalPrice(UserEntity iuser);

    @Transactional
    void deleteByIuserAndItemid(UserEntity iUser, ItemEntity itemId);
}
