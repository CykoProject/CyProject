package com.example.CyProject.home.model.diary;

import com.example.CyProject.shopping.model.item.ItemEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "home_diary")
@Getter
@Setter
@ToString
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idiary;

    private int ihost;
    private String ctnt;

    @Column(insertable = false, updatable = false)
    private LocalDateTime rdt;
}
