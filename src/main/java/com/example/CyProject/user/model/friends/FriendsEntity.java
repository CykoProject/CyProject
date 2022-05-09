package com.example.CyProject.user.model.friends;

import com.example.CyProject.home.model.home.HomeEntity;
import com.example.CyProject.user.model.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "friends")
@Entity
@Getter
@Setter
@ToString
public class FriendsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ifriend;

    private int iuser;

    @OneToOne
    @JoinColumn(name = "fuser")
    private UserEntity fuser;

    private int status;
    private String nickname;

    @Column(insertable = false)
    private LocalDateTime rdt;
}
