package com.example.CyProject.home.model.friends;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendsMapper {
    int selFriends(FriendsEntity entity);
}
