package com.example.CyProject.user.model;

import com.example.CyProject.config.Role;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "user")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iuser;

    private String email;
    private String nm;
    private String upw;

    @Column(insertable = false)
    private String provider;

    @Column(insertable = false)
    private String img;

    @Column(insertable = false)
    private int point;

    @Column(insertable = false)
    private Date rdt;

    private Role role;
}