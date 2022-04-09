package com.example.CyProject.main;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Service
public class MainWebtoonApiService {
    {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);

        String dayToStr = "";

        switch(day) {
            case 1:
                dayToStr="sun";
                break;
            case 2:
                dayToStr="mon";
                break;
            case 3:
                dayToStr="tue";
                break;
            case 4:
                dayToStr="wed";
                break;
            case 5:
                dayToStr="thu";
                break;
            case 6:
                dayToStr="fri";
                break;
            case 7:
                dayToStr="sat";
                break;
        }

        String URL = "https://comic.naver.com/webtoon/weekdayList?week=" + dayToStr; //원하는 url

        Connection conn = Jsoup.connect(URL);

        Document document;

        List monWtInfo = new ArrayList<>();


        try {
            document = conn.get();
            Elements monTitles = document.select("ul.img_list > li > dl > dt > a");

            for (Element element : monTitles) {

                    if (monTitles.indexOf(element) <= 4) {
                        monWtInfo.add(element.text());
                    }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dayToStr);
        System.out.println(monWtInfo);
    }



}

