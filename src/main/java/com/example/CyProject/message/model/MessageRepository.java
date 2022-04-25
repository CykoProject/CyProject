package com.example.CyProject.message.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    @Query("SELECT COUNT (m) FROM MessageEntity m WHERE m.receiver.iuser = ?1 AND m.recv_read = false")
    int beforeReadMsgCnt(int receiver);

    @Query("SELECT m FROM MessageEntity m WHERE m.iuser.iuser = ?1 ORDER BY m.rdt DESC ")
    List<MessageEntity> getSendMsgList(int iuser);;

    @Query("SELECT m FROM MessageEntity m WHERE m.receiver.iuser = ?1 ORDER BY m.rdt DESC")
    List<MessageEntity> getMsgList(int receiver);

    @Modifying
    @Transactional
    @Query("UPDATE MessageEntity SET recv_read = true WHERE imsg = ?1")
    int updRecvRead(int imsg);
}
