package com.example.CyProject.user.model.friends;


import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsService {
    @Autowired
    private FriendMapper friendMapper;
    @Autowired FriendsRepository friendsRepository;

    @Autowired private UserRepository userRepository;

    public List<FriendsEntity> selectFriendsList(int iuser){
        FriendsEntity entity  = new FriendsEntity();
        entity.setIuser(iuser);
        return friendMapper.selectFriendsList(entity);
    }

    public boolean isFriend(int iuser, int fuser, String nickname) {
        UserEntity user = new UserEntity();
        user.setIuser(fuser);
        int cnt = friendsRepository.countAllByIuserAndFuserOrNickname(iuser, user, nickname);
        boolean result = cnt > 0 ? false : true;

        return result;
    }
    public UserEntity getUserData(int iuser) {
        UserEntity user = userRepository.findByIuser(iuser);
        return user;
    }
}
