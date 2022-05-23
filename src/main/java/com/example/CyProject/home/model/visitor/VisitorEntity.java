package com.example.CyProject.home.model.visitor;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "home_visitor")
public class VisitorEntity {

    @EmbeddedId
    private VisitorPk visitorPk;
}
