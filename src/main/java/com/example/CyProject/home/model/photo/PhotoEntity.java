package com.example.CyProject.home.model.photo;

import com.example.CyProject.user.model.UserEntity;
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
public class PhotoEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iphoto;

    @OneToOne
    @JoinColumn(name = "ihost")
    private UserEntity ihost;

    private int ifile;
    private String title;
    private String ctnt;

    @Column(insertable = false)
    private LocalDateTime rdt;

    @Column(insertable = false)
    private int scrap;
}
