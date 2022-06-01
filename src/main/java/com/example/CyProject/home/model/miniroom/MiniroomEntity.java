package com.example.CyProject.home.model.miniroom;

import com.example.CyProject.shopping.model.item.ItemEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table (name = "home_mini_room")
@Data
public class MiniroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iroom;

    private int ihost;

    @OneToOne
    @JoinColumn(name = "myroom")
    private ItemEntity myroom;

    @Column(insertable = false)
    private boolean repre;
}
