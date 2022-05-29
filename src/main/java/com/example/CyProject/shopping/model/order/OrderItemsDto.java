package com.example.CyProject.shopping.model.order;

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
