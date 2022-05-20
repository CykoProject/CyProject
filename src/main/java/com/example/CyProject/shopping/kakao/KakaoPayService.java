package com.example.CyProject.shopping.kakao;

import java.net.URI;
import java.net.URISyntaxException;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;
@Service
@Log
public class KakaoPayService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    AuthenticationFacade authenticationFacade;


    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;

    public String kakaoPayReady(KakaoPayDto dto) {

        UserEntity entity = new UserEntity();
        entity.setIuser(authenticationFacade.getLoginUserPk());

        //상품 총 액(장바구니 아이템 총액)
//        int totalAmount = 0;
//        for (int i=0; i<cartRepository.findAllByIuser(entity).size(); i++) {
//            totalAmount += cartRepository.findAllByIuser(entity).get(i).getItemid().getPrice() * cartRepository.findAllByIuser(entity).get(i).getCnt();
//        }

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "daaaf9746ca0938c2fbdf8d408705df2");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", String.valueOf(Math.random()*100000000+1));
        params.add("partner_user_id", authenticationFacade.getLoginUser().getEmail());
        params.add("item_name", dto.getItem_id());
        params.add("quantity", String.valueOf(dto.getQuantity()));
        params.add("total_amount", String.valueOf(dto.getTotal_amount()));
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8090/shopping/kakaoPay/success");
        params.add("cancel_url", "http://localhost:8090/shopping/kakaoPay/cancel");
        params.add("fail_url", "http://localhost:8090/shopping/kakaoPay/successFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            log.info("" + kakaoPayReadyVO);

            return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/pay";

    }
}
