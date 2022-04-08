package com.example.CyProject.home.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "home")
@Getter
@Setter
@ToString
public class HomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ihome;

    private int iuser;
    private boolean diary;
    private boolean photo;
    private boolean visit;
    private boolean jukebox;
    private boolean mini_room;
    private int scrap_cnt;
    private int daily_visit;
    private int total_visit;
    private LocalDateTime rdt;
}
