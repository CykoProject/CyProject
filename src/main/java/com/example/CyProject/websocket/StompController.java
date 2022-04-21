package com.example.CyProject.websocket;

import com.example.CyProject.user.model.friends.FriendsEntity;
import com.example.CyProject.user.model.friends.FriendsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Controller
public class StompController {
    @Autowired private SimpMessagingTemplate simpMsg;
    @Autowired private FriendsRepository friendsRepository;


    int onlineUserCnt = 0;
    private List<Integer> onlineUsers = new ArrayList<>();
    private Map<Integer, List<Integer>> friendList = new HashMap<>();
    private Map<Integer, Integer> onlineCnt = new HashMap<>();
    @MessageMapping("/user")
    public void users(int loginUser) {
        /*
         * onlineUserCnt : 새로고침 막기위한 변수
         *
         * friendList : 로그인 한 회원들의 일촌들
         *              Map<iuser, friendList>
         *
         * onlineCnt : 로그인 한 일촌 수
         *             Map<iuser, 로그인 되어있는 일촌 수>
         *
         * loginUser : 로그인 시도한 유저 PK
         *
         * chk : 로그인 중복처리 용도
         *
         * onlineUsers : 로그인 한 유저 일촌들 저장
         *
         * friends : 현재 로그인 한 회원의 친구들
         */

        Map<String, Object> payload = new HashMap<>();

        int chk = 0;
        for(Integer item : onlineUsers) {
            if(item == loginUser) {
                chk++;
            }
        }
        if(chk == 0) {
            onlineUsers.add(loginUser);
        }

        for(Integer item : onlineUsers) {
            List<Integer> friends = friendsRepository.selectFriendsList(item);
            List<Integer> sendToFriend = friendsRepository.selectIuserFromFuser(item);

            for(Integer list : sendToFriend) {
                friends.add(list);
            }
            friendList.put(item, friends);
        }

        List<Integer> friends = friendList.get(loginUser);
        System.out.println(friends);
        System.out.println(onlineUsers);
        if(onlineUserCnt == 0 || onlineUserCnt != onlineUsers.size()) {
            for (Integer item : friends) {
                int cnt = 0;
                for (Integer user : onlineUsers) {
                    if (onlineCnt.get(user) != null && onlineCnt.get(user) > 0) {
                        cnt = onlineCnt.get(user);
                    }
                    if (item == user) { // 방금 로그인 한 유저pk 랑 일촌일 경우
                        cnt++;
                        onlineCnt.put(loginUser, cnt); // 방금 로그인 한 pk
                    }
                    onlineCnt.put(user, cnt);
                    cnt = 0;
                }
            }
        }

        onlineUserCnt = onlineCnt.size();
        payload.put("result", onlineCnt);
        simpMsg.convertAndSend("/sub/user",payload);
    }
}
