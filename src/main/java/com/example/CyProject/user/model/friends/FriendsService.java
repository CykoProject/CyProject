package com.example.CyProject.user.model.friends;


import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsService {
    @Autowired
    private FriendMapper friendMapper;

    public List<FriendsEntity> selectFriendsList(int iuser){
        FriendsEntity entity  = new FriendsEntity();
        entity.setIuser(iuser);
        return friendMapper.selectFriendsList(entity);
    }
}
