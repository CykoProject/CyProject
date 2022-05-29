package com.example.CyProject.shopping.kakao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.shopping.CartApiController;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryEntity;
import com.example.CyProject.shopping.model.history.purchase.PurchaseHistoryRepository;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.order.OrderInfoEntity;
import com.example.CyProject.shopping.model.order.OrderInfoRepository;
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
    @Autowired
    OrderInfoRepository orderInfoRepository;
    @Autowired
    CartApiController cartApiController;
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;


    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;
    private String totalAmount = null;

    public String kakaoPayReady() {

        UserEntity entity = new UserEntity();
        entity.setIuser(authenticationFacade.getLoginUserPk());

        System.out.println("orderinforepositoryfindby.. : " + orderInfoRepository.findTopByIuserOrderByRdtDesc(entity));

        OrderInfoEntity orderInfoEntity;
        orderInfoEntity = orderInfoRepository.findTopByIuserOrderByRdtDesc(entity);


        //상품 총 액(장바구니 아이템 총액)
//        int totalAmount = 0;
//        for (int i=0; i<cartRepository.findAllByIuser(entity).size(); i++) {
//            totalAmount += cartRepository.findAllByIuser(entity).get(i).getItemid().getPrice() * cartRepository.findAllByIuser(entity).get(i).getCnt();
//        }

        //아이템 코드
        List<ItemEntity> itemIdList = new ArrayList<>();
        itemIdList.addAll(purchaseHistoryRepository.purchaseItemIdList(entity));

//        String item_code = "";
//
//        for (int i=0; i< itemIdList.size(); i++) {
//            if (i<itemIdList.size()-1) {
//                item_code += (itemIdList.get(i).getItem_id()+",");
//            } else  {
//                item_code += itemIdList.get(i).getItem_id();
//            }
//        }
//        System.out.println(item_code);

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "daaaf9746ca0938c2fbdf8d408705df2");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Max-Age", "3600");
        headers.add("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", String.valueOf(orderInfoEntity.getOrder_id()));
        params.add("partner_user_id", authenticationFacade.getLoginUser().getEmail());
        params.add("item_name", orderInfoEntity.getItem_nm());
        params.add("quantity", String.valueOf(orderInfoEntity.getQuantity()));
        params.add("total_amount", String.valueOf(orderInfoEntity.getTotal_amount()));
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8090/shopping/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8090/shopping/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8090/shopping/kakaoPaySuccessFail");

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

        return "/shopping/kakaoPayCancel";

    }

    public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        UserEntity entity = new UserEntity();
        entity.setIuser(authenticationFacade.getLoginUserPk());

        //아이템 코드
//        List<ItemEntity> itemIdList = new ArrayList<>();
//        itemIdList.addAll(purchaseHistoryRepository.purchaseItemIdList(entity));
//
//        String item_code ="";

//        for (int i=0; i< itemIdList.size(); i++) {
//            if (i<itemIdList.size()-1) {
//                item_code += (itemIdList.get(i).getItem_id()+",");
//            } else  {
//                item_code += itemIdList.get(i).getItem_id();
//            }
//        }

        OrderInfoEntity orderInfoEntity;
        orderInfoEntity = orderInfoRepository.findTopByIuserOrderByRdtDesc(entity);

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "daaaf9746ca0938c2fbdf8d408705df2");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", String.valueOf(orderInfoEntity.getOrder_id()));
        params.add("partner_user_id", authenticationFacade.getLoginUser().getEmail());
        params.add("pg_token", pg_token);
        params.add("total_amount", String.valueOf(orderInfoEntity.getTotal_amount()));

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return kakaoPayApprovalVO;
    }
}
