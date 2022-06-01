package com.example.CyProject.user.model.friends;


import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

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

    public boolean isFriend(int iuser, int fuser) {
        UserEntity user = new UserEntity();
        user.setIuser(fuser);
        int cnt = friendsRepository.countAllByIuserAndFuser(iuser, user);
        boolean result = cnt > 0 ? false : true;

        return result;
    }
    public UserEntity getUserData(int iuser) {
        UserEntity user = userRepository.findByIuser(iuser);
        return user;
    }

    public static String convertTelNo(String src) {

        String mobTelNo = src;

        if (mobTelNo != null) {
            mobTelNo = mobTelNo.replaceAll(Pattern.quote("-"), "");

            if (mobTelNo.length() == 11) {
                mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 7) + "-" + mobTelNo.substring(7);
            } else if (mobTelNo.length() == 8) {
                mobTelNo = mobTelNo.substring(0, 4) + "-" + mobTelNo.substring(4);
            } else {
                if (mobTelNo.startsWith("02")) {
                    mobTelNo = mobTelNo.substring(0, 2) + "-" + mobTelNo.substring(2, 5) + "-" + mobTelNo.substring(5);
                } else {
                    mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 6) + "-" + mobTelNo.substring(6);
                }
            }

        }

        return mobTelNo;
    }
}
