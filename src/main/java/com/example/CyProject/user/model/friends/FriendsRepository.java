package com.example.CyProject.user.model.friends;

import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

public interface FriendsRepository extends JpaRepository<FriendsEntity, Integer> {

    @Query("SELECT f.fuser.iuser FROM FriendsEntity f WHERE f.iuser = ?1 AND f.status = 1")
    List<Integer> selectFriendsPkList(int iuser);

    @Query("SELECT f FROM FriendsEntity f WHERE f.iuser = ?1 AND f.status = 1")
    List<FriendsEntity> selectFriendsList(int iuser);

    @Query("SELECT count(f) FROM FriendsEntity f WHERE f.fuser.iuser = ?1 AND f.status = 0")
    int countByReceiver(int receiver);

    int countAllByIuserAndFuserOrNickname(int iuser, UserEntity fuser, String nickname);

    @Query("SELECT f FROM FriendsEntity f WHERE f.fuser.iuser = ?1 AND f.status = 0")
    List<FriendsEntity> selectfuserFriends(int iuser);


}
