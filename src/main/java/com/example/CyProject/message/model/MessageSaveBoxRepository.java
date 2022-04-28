package com.example.CyProject.message.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageSaveBoxRepository extends JpaRepository<MessageSaveBoxEntity, Integer> {

    @Query("SELECT m from MessageSaveBoxEntity m where m.iuser.iuser = ?1")
    List<MessageSaveBoxEntity> selMsgSaveBoxList(int iuser);

    @Query("SELECT m.imsg from MessageSaveBoxEntity m where m.iuser.iuser = ?1")
    MessageEntity selDetailMsgSaveBox(int iuser);

    @Query("SELECT m from MessageSaveBoxEntity m where m.iuser.iuser = ?1 and m.imsg.imsg = ?2")
    MessageSaveBoxEntity selMsgBox(int iuser, int imsg);
}
