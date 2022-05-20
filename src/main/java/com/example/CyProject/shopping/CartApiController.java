package com.example.CyProject.shopping;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.shopping.model.order.OrderItemsDto;
import com.example.CyProject.shopping.model.cart.CartDto;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.shopping.model.history.PurchaseHistoryEntity;
import com.example.CyProject.shopping.model.history.PurchaseHistoryRepository;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.order.OrderInfoEntity;
import com.example.CyProject.shopping.model.order.OrderInfoRepository;
import com.example.CyProject.user.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    OrderInfoRepository orderInfoRepository;
    @Autowired
    AuthenticationFacade authenticationFacade;

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

    @PostMapping("/orderInfo")
    public int orderInfo(@RequestBody OrderItemsDto dto) {
        System.out.println("orderdto" + dto);

        UserEntity userEntity = new UserEntity();
        userEntity.setIuser(authenticationFacade.getLoginUserPk());




        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setItem_nm(dto.getItem_nm());
        orderInfoEntity.setQuantity(dto.getQuantity());
        orderInfoEntity.setTotal_amount(dto.getTotal_amount());
        orderInfoEntity.setIuser(userEntity);

        System.out.println(orderInfoRepository.save(orderInfoEntity));
        orderInfoRepository.save(orderInfoEntity);

        for (int i=0; i < dto.getItem_id().size(); i++) {
            PurchaseHistoryEntity entity = new PurchaseHistoryEntity();
            entity.setIuser(userEntity);

            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItem_id(dto.getItem_id().get(i));

            entity.setItem_id(itemEntity);
            entity.setCnt(dto.getItem_cnt().get(i));

            purchaseHistoryRepository.save(entity);
        }

        return 0;
    }
}
