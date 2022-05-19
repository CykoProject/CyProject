package com.example.CyProject.shopping;

import com.example.CyProject.shopping.model.cart.CartDto;
import com.example.CyProject.shopping.model.cart.CartEntity;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    @Autowired
    CartRepository cartRepository;

    @PostMapping("/add")
    public int saveItem(@RequestBody CartDto dto) {
        UserEntity u = new UserEntity();
        u.setIuser(dto.getIuser());
        ItemEntity i = new ItemEntity();
        i.setItem_id(dto.getItem_id());

        boolean isItem = cartRepository.findByIuserAndItemid(u, i) == null ? true : false;

        if(isItem) {
            cartRepository.save(dto.toEntity());
        } else {
            cartRepository.updCartCnt(u, i);
        }

        return 0;
    }

    @Transactional
    @PostMapping("/subtract")
    public int subtractItem(@RequestBody CartDto dto) {
        UserEntity u = new UserEntity();
        u.setIuser(dto.getIuser());
        ItemEntity i = new ItemEntity();
        i.setItem_id(dto.getItem_id());

        boolean isItem = cartRepository.findByIuserAndItemid(u, i).getCnt() > 1 ? true : false;

        if(isItem) {
            cartRepository.subtractCartCnt(u, i);
        }
        return 0;
    }

    @Transactional
    @PostMapping("/delete")
    public int deleteItem(@RequestBody CartDto dto) {
        UserEntity u = new UserEntity();
        u.setIuser(dto.getIuser());
        ItemEntity i = new ItemEntity();
        i.setItem_id(dto.getItem_id());

        System.out.println(u);
        System.out.println(i);

        cartRepository.deleteByIuserAndItemid(u, i);
        return 0;
    }


}
