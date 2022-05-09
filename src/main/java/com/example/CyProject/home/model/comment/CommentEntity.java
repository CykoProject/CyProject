package com.example.CyProject.home.model.comment;

import com.example.CyProject.home.HomeCategory;
import com.example.CyProject.user.model.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@ToString
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int icmt;

    @OneToOne
    @JoinColumn(name = "ihost")
    private UserEntity ihost;

    private int category;

    private int iboard;

    private String ctnt;

    @OneToOne
    @JoinColumn(name = "writer")
    private UserEntity writer;

    private int parent;

    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;

}
