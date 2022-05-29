package com.example.CyProject.user.model.friends;

import com.example.CyProject.user.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface FriendMapper {

    List<FriendsEntity> selectFriendsList(FriendsEntity entity);

    int selFriends(@Param("iuser") int iuser, @Param("fuser") int fuser);
}
