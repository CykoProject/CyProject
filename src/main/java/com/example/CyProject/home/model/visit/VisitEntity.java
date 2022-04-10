package com.example.CyProject.home.model.visit;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_visit")
@Getter
@Setter
@ToString
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ivisit;

    private int ihost;

    private String ctnt;

    private int iuser;

    private boolean secret;

    @Column(insertable = false)
    private LocalDateTime rdt;
}
