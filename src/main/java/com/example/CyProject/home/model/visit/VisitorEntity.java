package com.example.CyProject.home.model.visit;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "home_visitor")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisitorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ihome;

    private int iuser;

    private Date rdt;
}
