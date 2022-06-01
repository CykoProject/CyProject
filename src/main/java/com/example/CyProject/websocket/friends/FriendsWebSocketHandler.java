package com.example.CyProject.websocket.friends;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.friends.FriendsEntity;
import com.example.CyProject.user.model.friends.FriendsRepository;
import com.example.CyProject.user.model.friends.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class FriendsWebSocketHandler extends TextWebSocketHandler {

    @Autowired private FriendsRepository friendsRepository;
    @Autowired private FriendsService friendsService;

    private static List<WebSocketSession> list = new ArrayList<>();
    private static Map<String, Integer> mappingId = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String[] strArr = payload.split("=");

        switch (strArr[0]) {
            case "fopen" :
                int loginUser = Integer.parseInt(strArr[1]);
                mappingId.put(session.getId(), loginUser);
                break;

            case "add" :
                String[] userArr = strArr[1].split(",");
                int sender = Integer.parseInt(userArr[0]);
                int receiver = Integer.parseInt(userArr[1]);
                String nickname = userArr[2];

                FriendsEntity entity = new FriendsEntity();
                UserEntity userEntity = new UserEntity();
                entity.setIuser(sender);
                userEntity.setIuser(receiver);
                entity.setFuser(userEntity);
                entity.setNickname(nickname);

                if(friendsService.isFriend(sender, receiver)) {
                    friendsRepository.save(entity);
                }

                int cnt = friendsRepository.countByReceiver(receiver);
                String msg = "add=" + cnt;
                TextMessage tm = new TextMessage(msg);

                for(WebSocketSession wess : list) {
                    if(mappingId.get(wess.getId()) == receiver) {
                        wess.sendMessage(tm);
                    }
                }
                break;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus ct) throws Exception {
        list.remove(session);
    }
}
