package com.example.CyProject;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private LocalDateTime startDate;
    private LocalDateTime tomorrow;

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

    public static class DateBuilder {
        private LocalDateTime startDate;
        private LocalDateTime tomorrow;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        public DateBuilder() {

        }

        public DateBuilder startDate(String startDate) {
            String strStartDate = startDate + " 00:00:00";
            LocalDateTime result = LocalDateTime.parse(strStartDate, formatter);
            this.startDate = result;
            return this;
        }

        public DateBuilder tomorrow(String tomorrow) {
            String strTomorrow = tomorrow + " 00:00:01";
            LocalDateTime result = LocalDateTime.parse(strTomorrow, formatter);
            result = result.plusDays(1);
            this.tomorrow = result;
            return this;
        }

        public PageEntity build() {
            return new PageEntity(this);
        }
    }

    public PageEntity(DateBuilder builder) {
        this.startDate = builder.startDate;
        this.tomorrow = builder.tomorrow;
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
