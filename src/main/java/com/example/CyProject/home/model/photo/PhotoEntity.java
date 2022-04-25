package com.example.CyProject.home.model.photo;

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

    private int ihost;
    private String title;
    private String ctnt;

    @Column(insertable = false)
    private LocalDateTime rdt;

    @Column(insertable = false)
    private int scrap;
}
