package com.example.CyProject.home.model.home;

import com.example.CyProject.user.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_message")
@Getter
@Setter
@ToString
public class HomeMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imsg;

    private int ihost;
    private String ctnt;

    private int writer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;

    @Transient
    private String nm;

    @Transient
    private String nickname;

}
