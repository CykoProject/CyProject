package com.example.CyProject.home.model.photo;

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
    private int iphoto;

    private String img;
}
