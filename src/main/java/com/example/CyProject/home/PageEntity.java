package com.example.CyProject.home;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Getter
@ToString
public class PageEntity {

    private int maxPage;
    private int rowCnt;
    private int pageCnt;
    private int startPage;
    private int lastPage;
    private int page;
    private int pop;

    public static class Builder {

        private int maxPage;
        private int rowCnt;
        private int pageCnt;
        private int page;


        public Builder() {

        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder pageCnt(int pageCnt) {
            this.pageCnt = pageCnt;
            return this;
        }

        public Builder rowCnt(int rowCnt) {
            this.rowCnt = rowCnt;
            return this;
        }

        public Builder maxPage(int maxPage) {
            this.maxPage = maxPage;
            return this;
        }

        public PageEntity build() {
            return new PageEntity(this);
        }
    }

    public PageEntity(Builder builder) {
        this.page = builder.page;
        this.pageCnt = builder.pageCnt;
        this.rowCnt = builder.rowCnt;
        this.maxPage = builder.maxPage;

        this.pop = (int) Math.ceil((double) this.page / this.pageCnt);
        this.lastPage = pageCnt * pop;
        this.startPage = lastPage - (pageCnt - 1);
    }
}
