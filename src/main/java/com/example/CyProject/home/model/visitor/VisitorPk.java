package com.example.CyProject.home.model.visitor;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Embeddable
public class VisitorPk implements Serializable {

    @Column(name = "ihome")
    private int ihome;;

    @Column(name = "iuser")
    private int iuser;

    @Column(name = "rdt", insertable = false, updatable = false)
    private String rdt;
}
