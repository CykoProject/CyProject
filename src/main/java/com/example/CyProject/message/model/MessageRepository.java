package com.example.CyProject.message.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    @Query("SELECT COUNT (m) FROM MessageEntity m WHERE m.receiver.iuser = ?1 AND m.recv_read = false")
    int beforeReadMsgCnt(int receiver);

    @Query("SELECT m FROM MessageEntity m WHERE m.iuser.iuser = ?1 AND m.remove_iuser = false ORDER BY m.rdt DESC ")
    Page<MessageEntity> getSendMsgList(int iuser, Pageable pageable);

    @Query("SELECT m FROM MessageEntity m WHERE m.receiver.iuser = ?1 AND m.remove_receiver = false ORDER BY m.rdt DESC")
    Page<MessageEntity> getReceiveMsgList(int receiver, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE MessageEntity SET recv_read = true WHERE imsg = ?1")
    int updRecvRead(int imsg);

    @Modifying
    @Transactional
    @Query("UPDATE MessageEntity SET remove_iuser = ?1, remove_receiver = ?2 WHERE imsg = ?3 ")
    int delMsg(boolean iuser, boolean receiver, int imsg);
}
