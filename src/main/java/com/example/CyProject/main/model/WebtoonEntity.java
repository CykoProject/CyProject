package com.example.CyProject.main.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class WebtoonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iWebtoon;

    private String title;
    private String image;
    private String link;
    private String writer;
    private String userRating;

}
