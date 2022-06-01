package com.example.CyProject.home.model.scrap;

import com.example.CyProject.home.model.photo.PhotoEntity;
import com.example.CyProject.user.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BoardListId.class)
public class BoardListEntity {
    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "iuser")
    private UserEntity iuser;

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "iphoto")
    private PhotoEntity iphoto;

    private boolean scrap;
}
