package com.example.CyProject.main.model;

import com.example.CyProject.user.model.UserEntity;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "cmt")
public class CmtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int icmt;

    private String ctnt;

    @OneToOne
    @JoinColumn(name = "iuser")
    private UserEntity iuser;

    @Column(insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP()")
    private Date rdt;
}
