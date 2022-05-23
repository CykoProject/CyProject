package com.example.CyProject.shopping.model.item;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_id;

    private String nm;
    private String artist;
    private int price;
    private int icategory;
    private String file;

    @Column(insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP()")
    private Date rdt;
}
