package com.example.CyProject.user.model.friends;

import com.example.CyProject.main.model.top.TopHelper;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

public interface FriendsRepository extends JpaRepository<FriendsEntity, Integer> {

    String bestFriendsSql = "SELECT count(A.iuser) AS cnt, B.nm, B.email, B.iuser, B.profile_img AS img" +
            " FROM friends A" +
            " INNER JOIN `user` B" +
            " ON A.iuser = B.iuser" +
            " GROUP BY A.iuser" +
            " ORDER BY cnt DESC" +
            " LIMIT 5";

    @Query("SELECT f.fuser.iuser FROM FriendsEntity f WHERE f.iuser = ?1 AND f.status = 1")
    List<Integer> selectFriendsPkList(int iuser);

    @Query("SELECT f FROM FriendsEntity f WHERE f.iuser = ?1 AND f.status = 1")
    List<FriendsEntity> selectFriendsList(int iuser);

    @Query("SELECT count(f) FROM FriendsEntity f WHERE f.fuser.iuser = ?1 AND f.status = 0")
    int countByReceiver(int receiver);

    int countAllByIuserAndFuser(int iuser, UserEntity fuser);

    @Query("SELECT f FROM FriendsEntity f WHERE f.fuser.iuser = ?1 AND f.status = 0")
    List<FriendsEntity> selectfuserFriends(int iuser);

    @Query("SELECT f FROM FriendsEntity f WHERE f.iuser = ?1 AND f.status = 0")
    List<FriendsEntity> selecSenderList(int iuser);

    @Query("SELECT f FROM FriendsEntity f WHERE f.iuser = ?1 AND f.fuser.iuser = ?2")
    FriendsEntity hasFriendsByIuserAndFuser(int iuser, int fuser);

    @Query(value = bestFriendsSql, nativeQuery = true)
    List<TopHelper> getBestFriends();
}
