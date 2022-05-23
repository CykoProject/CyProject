package com.example.CyProject.user.model.points;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "point_history")
@Entity
@Data
public class PointHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ipoint;

    private int iuser;
    private int changed_point;
    private String reason;

    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;
}
