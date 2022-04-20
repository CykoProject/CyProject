package com.example.CyProject.home.model.report;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
@ToString
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ireport;

    private int icategory; // controller
    private int iboard;
    private int reporter; // authenticationFacade
    private String reason;
    private int iuser;

    @Column(insertable = false)
    private LocalDateTime rdt;
}
