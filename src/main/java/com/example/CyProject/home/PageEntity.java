package com.example.CyProject.home;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageEntity {
    private int maxPage;
    private int rowCnt;
    private int page;
    private int pop;

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setRowCnt(int rowCnt) {
        this.rowCnt = rowCnt;
        this.pop = (int) Math.ceil((double) this.page / rowCnt);
    }
}
