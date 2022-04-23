package com.example.CyProject.user.model.friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.LinkedList;
import java.util.List;

public interface FriendsRepository extends JpaRepository<FriendsEntity, Integer> {
    
    // 팔로잉
    @Query("SELECT f.fuser FROM FriendsEntity f WHERE f.iuser = ?1 AND f.status = 1")
    List<Integer> selectFriendsList(int iuser);

    // 팔로워
    @Query("SELECT f.iuser FROM FriendsEntity f WHERE f.fuser = ?1 AND f.status = 1")
    List<Integer> selectIuserFromFuser(int fuser);
}
