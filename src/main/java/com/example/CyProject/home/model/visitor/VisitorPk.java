package com.example.CyProject.home.model.visitor;

import com.example.CyProject.user.model.UserEntity;
import lombok.Data;
import org.apache.ibatis.annotations.One;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Embeddable
public class VisitorPk implements Serializable {

    @Column(name = "ihome")
    private int ihome;;

    @OneToOne
    @JoinColumn(name = "iuser")
    private UserEntity iuser;

    @Column(name = "rdt", insertable = false, updatable = false)
    private String rdt;
}
