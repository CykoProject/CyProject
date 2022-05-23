package com.example.CyProject.shopping.model.order;

import com.example.CyProject.shopping.model.cart.CartEntity;
import com.example.CyProject.shopping.model.history.PurchaseHistoryEntity;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.user.model.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemsDto {

    private List<Integer> item_cnt;
    private String item_nm;
    private List<Integer> item_id;
    private int quantity;
    private int total_amount;


    private List<OrderInfoEntity> orderInfoEntities;
}
