package com.example.CyProject.shopping.kakao;

import com.example.CyProject.shopping.model.cart.CartDto;
import com.example.CyProject.shopping.model.cart.CartRepository;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log
@Controller
@RequestMapping("/shopping")
public class KakaoPayController {

    @Autowired
    CartRepository cartRepository;

    @Setter(onMethod_ = @Autowired)
    private KakaoPayService kakaopay;


    @GetMapping("/kakaoPay")
    public void kakaoPayGet() {

    }

    @PostMapping("/kakaoPay")
    public String kakaoPay() {
        log.info("kakaoPay post............................................");
        return "redirect:" + kakaopay.kakaoPayReady();

    }

    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
    }

}
