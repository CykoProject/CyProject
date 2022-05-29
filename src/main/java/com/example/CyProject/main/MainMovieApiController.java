package com.example.CyProject.main;


import com.example.CyProject.main.model.MovieEntity;
import com.example.CyProject.main.model.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.CyProject.main.MainMovieApiService.callMovieApi;

@RestController
@RequestMapping("/api/movie")
public class MainMovieApiController {

    @Autowired
    MainMovieApiService mainMovieApiService;

    HashMap <String, String> titleList = new HashMap<>();

    @GetMapping
    public List<MovieEntity> mainMovieApi() throws JsonProcessingException {
        List<MovieEntity> movieInfo = new ArrayList();
//        System.out.println("getmapping 자리 : " + titleList);
        for(int i=1; i <= titleList.size(); i++) {
            movieInfo.add(callMovieApi(titleList.get("title"+ i)));
        }
//        System.out.println("movieInfo : " + movieInfo);
        return movieInfo;
    }

    @PostMapping
    public void mainMovieInsertApi(@RequestBody Map<String, String> titles) {
        titleList = (HashMap<String, String>) titles;
//        System.out.println(titleList);
    }

}