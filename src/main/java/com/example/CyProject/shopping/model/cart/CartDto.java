package com.example.CyProject.shopping.model.cart;

import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.user.model.UserEntity;
import lombok.Data;

@Data
public class CartDto {
    private int iuser;
    private int item_id;

    public CartEntity toEntity() {
        CartEntity c = new CartEntity();
        UserEntity u = new UserEntity();
        ItemEntity i = new ItemEntity();
        u.setIuser(this.iuser);
        i.setItem_id(this.item_id);
        c.setIuser(u);
        c.setItemid(i);

        return c;
    }
}
