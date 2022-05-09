package com.example.CyProject.message.model;

import com.example.CyProject.user.model.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "msg_savebox")
@Getter
@Setter
@ToString
public class MessageSaveBoxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ibox;

    @OneToOne
    @JoinColumn(name = "iuser")
    private UserEntity iuser;

    @OneToOne
    @JoinColumn(name = "imsg")
    private MessageEntity imsg;
}
