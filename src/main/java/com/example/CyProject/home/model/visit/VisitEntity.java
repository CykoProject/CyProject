package com.example.CyProject.home.model.visit;

import com.example.CyProject.user.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_visit")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ivisit;

    private int ihost;

    private String ctnt;

    @OneToOne
    @JoinColumn(name = "iuser")
    private UserEntity iuser;

    private boolean secret;

    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;
}
