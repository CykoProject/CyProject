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

    @Query("SELECT sum(c.itemid.price) from CartEntity c where c.iuser = :iuser")
    int totalPrice(UserEntity iuser);
//이제 되네 휴..... ㅋㅋㅋㅋ AS 때문이였네요... 빡친다 진짜... 한끝차이로 시간 날리니까 ㅋㅋㅋ ㅋㅋㅋ jpa는 그냥 객체로 하는거니까 뭐 해야되는걸로... 알면 되지않을까요,,,,
    // 맞음 내가 적응 해야지 뭐 ㅋㅋ ㅋㅋㅋㅋㅋㅋㅋ 오늘 일단 총 합이랑 여기 봐바

    void deleteByIuserAndItemid(int iUser, int itemId);
}
