package com.example.CyProject.home.model.friends;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friends")
@Getter
@Setter
@ToString
public class FriendsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ifriend;

    private int iuser;
    private int fuser;

    @Column(insertable = false)
    private int status;

    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;

    private String nickname;

}
