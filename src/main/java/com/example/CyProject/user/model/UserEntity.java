package com.example.CyProject.user.model;

import com.example.CyProject.config.Role;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicUpdate
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iuser;

    private String email;
    private String nm;
    private String upw;

    @Column(nullable = false)
    private String cellphone;

    @Column(insertable = false)
    private String provider;

    @Column(insertable = false)
    private String img;

    @Column(insertable = false)
    private int point;

    @Column(insertable = false)
    private Date rdt;

    private Role role;

    private String profile_img;
    private String profile_ctnt;
}