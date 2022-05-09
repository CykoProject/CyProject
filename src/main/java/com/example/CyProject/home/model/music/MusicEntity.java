package com.example.CyProject.home.model.music;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "music")
@Entity
@Getter
@Setter
@ToString
public class MusicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imusic;

    private String nm;
    private String artist;
    private String url;
    private String img;

    @Column(insertable = false, updatable = false)
    private int cnt;

    private int price;
}
