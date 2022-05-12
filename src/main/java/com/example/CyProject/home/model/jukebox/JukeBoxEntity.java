package com.example.CyProject.home.model.jukebox;

import com.example.CyProject.shopping.model.bgm.MusicEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "home_jukebox")
@Getter
@Setter
@ToString
public class JukeBoxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ijukebox;

    @OneToOne
    @JoinColumn(name = "imusic")
    private MusicEntity imusic;

    private int ihost;

    @Column(insertable = false, updatable = false)
    private boolean repre;
}
