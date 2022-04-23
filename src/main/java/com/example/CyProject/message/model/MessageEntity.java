package com.example.CyProject.message.model;

import com.example.CyProject.user.model.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "message")
@Entity
@Getter
@Setter
@ToString
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imsg;

    @OneToOne
    @JoinColumn(name = "iuser")
    private UserEntity iuser;
    private int receiver;

    @Column(insertable = false, updatable = false)
    private boolean recv_read;

    private String ctnt;

    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;
}
