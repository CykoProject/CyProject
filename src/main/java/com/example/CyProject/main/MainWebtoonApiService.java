package com.example.CyProject.main;

import com.example.CyProject.main.model.WebtoonEntity;
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

    public List<WebtoonEntity> getWebtoonInfo() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);

        String dayToStr = "";

        switch (day) {
            case 1:
                dayToStr = "sun";
                break;
            case 2:
                dayToStr = "mon";
                break;
            case 3:
                dayToStr = "tue";
                break;
            case 4:
                dayToStr = "wed";
                break;
            case 5:
                dayToStr = "thu";
                break;
            case 6:
                dayToStr = "fri";
                break;
            case 7:
                dayToStr = "sat";
                break;
        }

        String URL = "https://comic.naver.com/webtoon/weekdayList?week=" + dayToStr; //원하는 url

        Connection conn = Jsoup.connect(URL);

        Document document;

        List<WebtoonEntity> wtInfoList = null;
        try {
            document = conn.get();
            Elements wtTitles = document.select("ul.img_list > li > dl > dt > a");
            Elements wtImages = document.select("ul.img_list > li > div > a > img");
            Elements wtLinks = document.select("ul.img_list > li > div > a");
            Elements wtWriters = document.select("ul.img_list > li > dl > dd.desc > a");
            Elements wtUserRatings = document.select("div.rating_type > strong");

            List wtTitleList = new ArrayList();
            List wtImageList = new ArrayList();
            List wtLinkList = new ArrayList();
            List wtWriterList = new ArrayList();
            List wtUserRatingList = new ArrayList();

            for (Element element : wtTitles) {

                if (wtTitles.indexOf(element) < 20) {
                    wtTitleList.add(element.text());
                }
            }

            for (Element element : wtImages) {

                if (wtImages.indexOf(element) < 20) {
                    wtImageList.add(element.attr("src"));
                }
            }

            for (Element element : wtLinks) {

                if (wtLinks.indexOf(element) < 20) {
                    wtLinkList.add("https://comic.naver.com" + element.attr("href"));
                }
            }

            for (Element element : wtWriters) {

                if (wtWriters.indexOf(element) < 20) {
                    wtWriterList.add(element.text());
                }
            }

            for (Element element : wtUserRatings) {

                if (wtUserRatingList.indexOf(element) < 20) {
                    wtUserRatingList.add(element.text());
                }
            }

            wtInfoList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                WebtoonEntity webtoonEntity = new WebtoonEntity();

                webtoonEntity.setIWebtoon(i + 1);
                webtoonEntity.setTitle((String) wtTitleList.get(i));
                webtoonEntity.setImage((String) wtImageList.get(i));
                webtoonEntity.setLink((String) wtLinkList.get(i));
                webtoonEntity.setWriter((String) wtWriterList.get(i));
                webtoonEntity.setUserRating((String) wtUserRatingList.get(i));

                wtInfoList.add(webtoonEntity);

            }
            System.out.println(wtInfoList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return wtInfoList;
    }


}

