package com.example.CyProject.shopping.kakao;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.shopping.CartApiController;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryRepository;
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

        purchaseHistoryRepository.purchaseComplete(userEntity);
        orderInfoRepository.OrderComplete(userEntity);

        List<ItemEntity> itemIdList = new ArrayList<>();
        itemIdList.addAll(purchaseHistoryRepository.purchaseItemIdList(userEntity));

        itemIdList.forEach((item)-> cartRepository.deleteByIuserAndItemid(userEntity, item));
    }
}
