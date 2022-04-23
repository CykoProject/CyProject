package com.example.CyProject.message.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    @Query("SELECT COUNT (m) FROM MessageEntity m WHERE m.receiver = ?1 AND m.recv_read = false")
    int beforeReadMsgCnt(int receiver);
}
