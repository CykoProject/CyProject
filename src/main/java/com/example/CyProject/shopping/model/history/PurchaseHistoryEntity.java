package com.example.CyProject.shopping.model.history;

import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.user.model.UserEntity;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Table(name = "purchase_history")
@Entity
@Data
public class PurchaseHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ihistory;

    @OneToOne
    @JoinColumn(name = "iuser")
    private UserEntity iuser;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item_id;

    @Column(insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP()")
    private Date rdt;

    private int cnt;

    @Column(insertable = false)
    @ColumnDefault(value = "0")
    private int complete;
}
