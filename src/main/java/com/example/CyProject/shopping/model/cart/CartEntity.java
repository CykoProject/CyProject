package com.example.CyProject.shopping.model.cart;

import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.user.model.UserEntity;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "cart")
@Entity
@Data
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int icart;

    @OneToOne
    @JoinColumn(name = "iuser")
    private UserEntity iuser;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemEntity itemid;

    @Column(insertable = false)
    @ColumnDefault(value = "1")
    private int cnt;

    @Column(insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP()")
    private Date rdt;
}
