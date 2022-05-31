package com.example.CyProject.home.model.photo;

import com.example.CyProject.user.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_photos")
@Getter
@Setter
@ToString
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iphoto;

    @OneToOne
    @JoinColumn(name = "ihost")
    private UserEntity ihost;

    private int ifile;
    private String title;
    private String ctnt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;

    @Column(insertable = false)
    private int scrap;
}
