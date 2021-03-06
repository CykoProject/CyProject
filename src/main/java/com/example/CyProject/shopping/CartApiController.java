package com.example.CyProject.shopping;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.jukebox.JukeBoxEntity;
import com.example.CyProject.home.model.jukebox.JukeBoxRepository;
import com.example.CyProject.home.model.miniroom.MiniroomEntity;
import com.example.CyProject.home.model.miniroom.MiniroomRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseInterface;
import com.example.CyProject.shopping.model.order.OrderItemsDto;
import com.example.CyProject.shopping.model.cart.CartDto;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryEntity;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryRepository;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.order.OrderInfoEntity;
import com.example.CyProject.shopping.model.order.OrderInfoRepository;
import com.example.CyProject.user.model.UserEntity;
import com.example.CyProject.user.model.UserRepository;
import com.example.CyProject.user.model.points.PointHistoryEntity;
import com.example.CyProject.user.model.points.PointHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    UserRepository userRepository;
    @Autowired
    PointHistoryRepository pointHistoryRepository;
    @Autowired
    JukeBoxRepository jukeBoxRepository;
    @Autowired
    MiniroomRepository miniroomRepository;

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

        System.out.println("???????????? 0 : " + purchaseHistoryRepository.isIncompletePurchase(userEntity));

        if (purchaseHistoryRepository.isIncompletePurchase(userEntity) > 0) {
            purchaseHistoryRepository.deletePurchaseFailData(userEntity);
        }

        if (orderInfoRepository.isIncompleteOrder(userEntity) > 0) {
            orderInfoRepository.deleteOrderIncomplete(userEntity);
        }

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

        int hasDotori = userRepository.findByIuser(authenticationFacade.getLoginUserPk()).getPoint();
        int totalAmount = hasDotori + (dto.getTotal_amount()/100);

        PointHistoryEntity pointHistoryEntity1 = new PointHistoryEntity();
        pointHistoryEntity1.setChanged_point(dto.getTotal_amount()/100);
        pointHistoryEntity1.setReason("?????? ?????? ????????? ??????");
        pointHistoryEntity1.setIuser(authenticationFacade.getLoginUserPk());

        System.out.println(pointHistoryEntity1);
        pointHistoryRepository.save(pointHistoryEntity1); // dto.getTotal_amount(); - charge

        PointHistoryEntity pointHistoryEntity2 = new PointHistoryEntity();
        pointHistoryEntity2.setChanged_point(totalAmount * -1);
        pointHistoryEntity2.setReason("?????? ??????");
        pointHistoryEntity2.setIuser(authenticationFacade.getLoginUserPk());

        System.out.println(pointHistoryEntity2);
        pointHistoryRepository.save(pointHistoryEntity2); // totalAmount; - purchase amount

        userRepository.updDotori(0, authenticationFacade.getLoginUserPk());

        return 0;
    }

    @PostMapping("/orderInfoPoint")
    public int orderInfoPoint(@RequestBody OrderItemsDto dto) {

        System.out.println("orderdto" + dto);

        UserEntity userEntity = new UserEntity();
        userEntity.setIuser(authenticationFacade.getLoginUserPk());

        if (purchaseHistoryRepository.isIncompletePurchase(userEntity) > 0) {
            purchaseHistoryRepository.deletePurchaseFailData(userEntity);
        }

        for (int i=0; i < dto.getItem_id().size(); i++) {
            PurchaseHistoryEntity entity = new PurchaseHistoryEntity();
            entity.setIuser(userEntity);

            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItem_id(dto.getItem_id().get(i));

            entity.setItem_id(itemEntity);
            entity.setCnt(dto.getItem_cnt().get(i));

            purchaseHistoryRepository.save(entity);
        }

        System.out.println("????????? ?????? : " + dto.getTotal_amount());

        int hasDotori = userRepository.findByIuser(authenticationFacade.getLoginUserPk()).getPoint();
        int updDotori = hasDotori - dto.getTotal_amount();

        userRepository.updDotori(updDotori, authenticationFacade.getLoginUserPk());

        PointHistoryEntity pointHistoryEntity = new PointHistoryEntity();
        pointHistoryEntity.setChanged_point(dto.getTotal_amount()*-1);
        pointHistoryEntity.setReason("?????? ??????");
        pointHistoryEntity.setIuser(authenticationFacade.getLoginUserPk());

        pointHistoryRepository.save(pointHistoryEntity);
        userRepository.updDotori(updDotori, authenticationFacade.getLoginUserPk());

        List<ItemEntity> itemIdList = new ArrayList<>();
        itemIdList.addAll(purchaseHistoryRepository.purchaseItemIdList(userEntity));

        System.out.println("????????? itemid : " + itemIdList);

        List<PurchaseInterface> bgmIdList = new ArrayList<>();
        bgmIdList.addAll(purchaseHistoryRepository.purchaseBgmIdList(userEntity));

        for (PurchaseInterface item : bgmIdList) {
            JukeBoxEntity entity = new JukeBoxEntity();
            entity.setIhost(authenticationFacade.getLoginUserPk());
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItem_id(item.getItem_id());
            entity.setImusic(itemEntity);
            jukeBoxRepository.save(entity);
        }

        for(ItemEntity item : itemIdList) {
            if (item.getIcategory() == 5) {
                MiniroomEntity miniroomEntity = new MiniroomEntity();
                miniroomEntity.setIhost(authenticationFacade.getLoginUserPk());
                miniroomEntity.setRepre(false);
                miniroomEntity.setMyroom(item);
                if (miniroomRepository.countByIhostAndMyroom(miniroomEntity.getIhost(), miniroomEntity.getMyroom()) == 0) {
                    miniroomRepository.save(miniroomEntity);
                }
            }

//            ItemEntity itemEntity = new ItemEntity();
//            itemEntity.setItem_id(itemEntity.getItem_id());
            cartRepository.deleteByIuserAndItemid(userEntity, item);
        }
        System.out.println("?????? ????????? ?????? ??????");
        purchaseHistoryRepository.purchaseComplete(userEntity);
        return 0;
    }
}
