package com.example.CyProject.message.model;

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

    private int iuser;
    private int receiver;

    @Column(insertable = false, updatable = false)
    private boolean recv_read;

    private String ctnt;

    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;
}
