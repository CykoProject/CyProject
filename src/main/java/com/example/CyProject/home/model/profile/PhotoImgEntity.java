package com.example.CyProject.home.model.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "photos")
@Getter
@Setter
@ToString
public class PhotoImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iphoto;

    private String img;
}
