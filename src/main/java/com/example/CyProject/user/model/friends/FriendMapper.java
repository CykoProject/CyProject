package com.example.CyProject.user.model.friends;

import com.example.CyProject.user.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendMapper {

    List<FriendsEntity> selectFriendsList(FriendsEntity entity);
}
