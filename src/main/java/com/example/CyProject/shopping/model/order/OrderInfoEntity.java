package com.example.CyProject.shopping.model.order;

import com.example.CyProject.user.model.UserEntity;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "order_info")
@Entity
@Data
public class OrderInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    private String item_nm;

    private int quantity;

    private int total_amount;

    @OneToOne
    @JoinColumn(name = "iuser")
    private UserEntity iuser;

    @Column(insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP()")
    private Date rdt;

    @Column(insertable = false)
    @ColumnDefault(value = "0")
    private int complete;
}
