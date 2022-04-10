package com.example.CyProject.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class MainNewsApiController {

    @Autowired
    MainNewsApiService mainNewsApiService;

    @GetMapping
    public String mainNewsApi () throws JsonProcessingException {
         return  mainNewsApiService.callNewsApi("손흥민");
    }

    @GetMapping("/search")
    public String mainSearchedNewsApi (String searchTxt) throws JsonProcessingException {
        System.out.println(searchTxt);
        System.out.println("GetMapping(\"/search\") : " + mainNewsApiService.callNewsApi(searchTxt));
        return  mainNewsApiService.callNewsApi(searchTxt);
    }

    @PostMapping
    public void mainNewsInputApi(@RequestBody Map<String, String> searchTxt) throws JsonProcessingException {
        System.out.println(searchTxt.get("searchTxt"));
        mainSearchedNewsApi(searchTxt.get("searchTxt"));
    }
}
