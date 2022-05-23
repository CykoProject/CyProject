package com.example.CyProject.shopping.model.minime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "minime")
@Getter
@Setter
@ToString
public class MinimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iminime;

    private String nm;
    private String img;
    private int price;
}
