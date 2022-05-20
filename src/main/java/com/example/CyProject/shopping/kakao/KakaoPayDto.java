package com.example.CyProject.shopping.kakao;

import lombok.Data;

@Data
public class KakaoPayDto {

    private String item_id;
    private int quantity;
    private int total_amount;
}
