package com.example.CyProject.shopping.model.cart;

import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.user.model.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private ItemEntity item_id;
    
    private int cnt;

    private Date rdt;
}
