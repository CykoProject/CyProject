package com.example.CyProject.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class MainNewsApiController {

    @Autowired
    MainNewsApiService mainNewsApiService;

    @GetMapping
    public String mainNewsApi () throws JsonProcessingException {
         return  mainNewsApiService.callNewsApi(); //여기서 리턴해주는데 왜 포스트맨에서
    }
}
