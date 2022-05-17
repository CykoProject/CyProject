package com.example.CyProject.message.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageSaveBoxRepository extends JpaRepository<MessageSaveBoxEntity, Integer> {

    @Query("SELECT m from MessageSaveBoxEntity m where m.iuser.iuser = ?1")
    Page<MessageSaveBoxEntity> selMsgSaveBoxList(int iuser, Pageable pageable);

    @Query("SELECT m.imsg from MessageSaveBoxEntity m where m.iuser.iuser = ?1")
    MessageEntity selDetailMsgSaveBox(int iuser);

    @Query("SELECT m from MessageSaveBoxEntity m where m.iuser.iuser = ?1 and m.imsg.imsg = ?2")
    MessageSaveBoxEntity selMsgBox(int iuser, int imsg);


    @Modifying
    @Transactional
    @Query("DELETE from MessageSaveBoxEntity m where m.imsg.imsg = ?1 and m.iuser.iuser = ?2")
    int delMsgSaveBoxDetail(int imsg, int iuser);
}
