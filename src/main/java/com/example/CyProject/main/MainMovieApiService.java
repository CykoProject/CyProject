package com.example.CyProject.main;

import com.example.CyProject.main.model.MovieEntity;
import com.example.CyProject.main.model.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Service
public class MainMovieApiService {

    @Autowired
    MovieRepository movieRepository;

    public static MovieEntity callMovieApi(String movieNm) throws JsonProcessingException {
        String clientId = "hKGIHBgODN218BhjUUrY"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "gIOfjwRweQ"; //애플리케이션 클라이언트 시크릿값"

        String text = null;
        try {
            text = URLEncoder.encode(movieNm, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/movie?query=" + text + "&display=1";    // json 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

//        System.out.println(responseBody);

        JSONObject movieInfo = new JSONObject(responseBody);
        JSONArray naverMovieResult = movieInfo.getJSONArray("items");
//        System.out.println(naverMovieResult);

        Iterator<Object> iter = naverMovieResult.iterator();

        List<String> list = new ArrayList<>();
        while(iter.hasNext()) {
            JSONObject naverMovie = (JSONObject) iter.next();
            list.add((String) naverMovie.get("title"));
            list.add((String) naverMovie.get("image"));
            list.add((String) naverMovie.get("link"));
            list.add(naverMovie.get("actor").toString().replaceAll("\\|$", ""));
            list.add(naverMovie.get("director").toString().replaceAll("\\|$", ""));
            list.add((String) naverMovie.get(("userRating")));
        }
        String title = list.get(0);
        String image = list.get(1);
        String link = list.get(2);
        String actor = list.get(3);
        String director = list.get(4);
        String userRating = list.get(5);

        MovieEntity entity = new MovieEntity();
        entity.setTitle(title);
        entity.setImage(image);
        entity.setLink(link);
        entity.setActor(actor);
        entity.setDirector(director);
        entity.setUserRating(userRating);

//        System.out.println(entity);

        return entity;
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

//    @Transactional
//    public void movieInsert(List titleList) {
//
//    }

}