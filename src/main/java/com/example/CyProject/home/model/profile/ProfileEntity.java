package com.example.CyProject.home.model.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_profile")
@Getter
@Setter
@ToString
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iprofile;

    private int ihost;
    private String ctnt;

    @Column(insertable = false)   // default 값 있는 컬럼에 있어야 함
    private String img;

    @Column(insertable = false)
    private LocalDateTime rdt;
}
