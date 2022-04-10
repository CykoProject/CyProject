package com.example.CyProject.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/webtoon")
public class MainWebtoonApiController {

    @Autowired
    MainWebtoonApiService mainWebtoonApiService;

    @GetMapping
    public List mainWebtoonApi () {
        return mainWebtoonApiService.getWebtoonInfo();
    }

}
