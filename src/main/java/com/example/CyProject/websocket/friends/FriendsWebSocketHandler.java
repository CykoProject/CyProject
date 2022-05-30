package com.example.CyProject.websocket.friends;

import com.example.CyProject.user.model.friends.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FriendsWebSocketHandler extends TextWebSocketHandler {

    @Autowired private FriendsRepository friendsRepository;

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userData = message.getPayload(); // add=1,3
        String[] typeArr = userData.split("=");

        switch (typeArr[0]) {
            case "add" :
                String[] splitStr = typeArr[1].split(",");

                /*
                    splitInteger[0] = 로그인 회원 pk
                    splitInteger[1] = 일촌신청 받는 사람 pk
                 */
                int[] splitInteger = Arrays.stream(splitStr).mapToInt(Integer::parseInt).toArray();

                //friendsRepository.save();

                // int count = friendsRepository.findByFuser();

                for(WebSocketSession sess : list) {
                    // add=1
                    sess.sendMessage(message);
                }
                break;

            case "online":
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
