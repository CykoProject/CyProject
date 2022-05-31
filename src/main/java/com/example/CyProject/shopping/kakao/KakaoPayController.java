package com.example.CyProject.shopping.kakao;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.home.model.jukebox.JukeBoxEntity;
import com.example.CyProject.home.model.jukebox.JukeBoxRepository;
import com.example.CyProject.shopping.CartApiController;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseInterface;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.order.OrderInfoRepository;
import com.example.CyProject.user.model.UserEntity;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log
@Controller
@RequestMapping("/shopping")
public class KakaoPayController {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    AuthenticationFacade authenticationFacade;
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    OrderInfoRepository orderInfoRepository;
    @Autowired
    CartApiController cartApiController;
    @Autowired
    JukeBoxRepository jukeBoxRepository;

    @Setter(onMethod_ = @Autowired)
    private KakaoPayService kakaopay;


//    @GetMapping("/kakaoPay")
//    public void kakaoPayGet() {
//
//    }

    @GetMapping("/kakaoPay")
    public String kakaoPay() {

        log.info("kakaoPay post............................................");
        return "redirect:" + kakaopay.kakaoPayReady();

    }

    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));

        UserEntity userEntity = new UserEntity();
        userEntity.setIuser(authenticationFacade.getLoginUserPk());

        List<ItemEntity> itemIdList = new ArrayList<>();
        itemIdList.addAll(purchaseHistoryRepository.purchaseItemIdList(userEntity));

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
//            ItemEntity itemEntity = new ItemEntity();
//            itemEntity.setItem_id(itemEntity.getItem_id());
            cartRepository.deleteByIuserAndItemid(userEntity, item);
        }

        System.out.println("구매 아이템 카트 삭제");


        purchaseHistoryRepository.purchaseComplete(userEntity);
        orderInfoRepository.OrderComplete(userEntity);


    }
}
