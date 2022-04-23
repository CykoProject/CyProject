package com.example.CyProject.websocket;

import com.example.CyProject.message.model.MessageRepository;
import com.example.CyProject.user.model.friends.FriendsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Autowired private FriendsRepository friendsRepository;
    @Autowired private MessageRepository messageRepository;

    private static List<WebSocketSession> list = new ArrayList<>();
    private static List<Integer> onlineUser = new ArrayList<>();
    private static Map<String, Integer> mappingId = new HashMap<>(); // sessionId = iuser
    Map<Integer, Integer> cntMap = new HashMap<>(); // js로 보내줄 애

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("payload : " + payload);
        String payloadArr[] = payload.split("=");
        String status = payloadArr[0];

        // 로그인
        if("open".equals(status)) {
            int iuser = Integer.parseInt(payloadArr[1]);
            String sessionId = session.getId();
            mappingId.put(sessionId, iuser);
            if (onlineUser.indexOf(iuser) == -1) {
                onlineUser.add(iuser);

//                List<Integer> result = selFriendList(iuser);
                List<Integer> result = friendsRepository.selectFriendsPkList(iuser);
                Collections.sort(onlineUser);
                List<Integer> list1 = result;
                List<Integer> list2 = onlineUser;

                /*
                 * list1 : 중복된 값 ( 온라인중인 일촌 )
                 * list2 : list2 와 중복된 값
                 */
                list1.retainAll(list2);
                int onlineCnt = list1.size();
                cntMap.put(iuser, onlineCnt);

                for (Integer item : list1) {
                    onlineCnt = cntMap.get(item);
                    cntMap.put(item, ++onlineCnt);
                }
            }
            JSONObject json = new JSONObject(cntMap);
            TextMessage msg = new TextMessage(json.toString());
            session.sendMessage(msg);
            for (WebSocketSession sess : list) {
                sess.sendMessage(msg);
            }

        } else if("msg".equals(status)) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> JSONData = mapper.readValue(payloadArr[1], Map.class);
            int receiver = Integer.parseInt(JSONData.get("receiver"));
//            int iuser = Integer.parseInt(JSONData.get("iuser"));
//            String ctnt = JSONData.get("ctnt");
            int msgCnt = messageRepository.beforeReadMsgCnt(receiver);

            String sessionId = null;
            for(String key : mappingId.keySet()) {
                if(mappingId.get(key) == receiver) {
                    sessionId = key;
                }
            }

            Map<String, Integer> msgMap = new HashMap<>();
            msgMap.put("msgCnt", msgCnt);
            JSONObject msg = new JSONObject(msgMap);
            TextMessage tm = new TextMessage(msg.toString());
            for(WebSocketSession sess : list) {
                if(sess.getId().equals(sessionId)) {
                    sess.sendMessage(tm);
                }
            }

        } else {

            for (WebSocketSession sess : list) {
                sess.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus ct) throws Exception {
        list.remove(session);
        System.out.println("연결종료");
        String sessionId = session.getId();
        int logoutIuser = mappingId.get(sessionId);

        if(onlineUser.contains(logoutIuser)) {
//            List<Integer> result = selFriendList(logoutIuser);
            List<Integer> result = friendsRepository.selectFriendsPkList(logoutIuser);
            for (Integer item : result) {
                int cnt = 0;
                try {
                    cnt = cntMap.get(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cnt != 0) {
                    cntMap.put(item, --cnt);
                }
            }
        }

        onlineUser.remove(onlineUser.indexOf(logoutIuser));
        cntMap.remove(logoutIuser);
        mappingId.remove(sessionId);
        JSONObject json = new JSONObject(cntMap);
        TextMessage msg = new TextMessage(json.toString());
        for(WebSocketSession sess : list) {
            sess.sendMessage(msg);
        }
    }
}
