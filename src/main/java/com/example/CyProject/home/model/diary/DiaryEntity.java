package com.example.CyProject.home.model.diary;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_diary")
@Getter
@Setter
@ToString
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idiary;

    private int ihost;
    private String ctnt;
    private LocalDateTime rdt;
}
