package com.example.CyProject.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "user")
@Getter
@Setter
@ToString
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(insertable = false)
    private int iuser;

    @Column
    private String uid;
    private String upw;
    private String nm;
}
