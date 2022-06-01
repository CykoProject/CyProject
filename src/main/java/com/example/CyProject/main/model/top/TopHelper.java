package com.example.CyProject.main.model.top;


public interface TopHelper {

    int getCnt();
    int getIuser();
    String getNm();
    String getImg();
    String getEmail();

    default TopVo toVisitorVo() {
        TopVo vo = new TopVo();
        vo.setCnt(getCnt());
        vo.setNm(getNm());
        vo.setEmail(getEmail());
        vo.setIuser(getIuser());
        vo.setImg(getImg());

        return vo;
    }
}
