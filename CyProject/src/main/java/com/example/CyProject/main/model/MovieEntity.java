package com.example.CyProject.main.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iMovie;

    private String title;
    private String image;
    private String link;
    private String actor;
    private String director;
    private String userRating;
}
