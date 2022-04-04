package com.example.CyProject.home;

import com.example.CyProject.home.model.HomeEntity;
import com.example.CyProject.home.model.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax/home")
public class HomeRestController {

    @Autowired private HomeRepository homeRepository;

    @GetMapping
    public HomeEntity home(HomeEntity entity) {
        return homeRepository.findByIuser(entity.getIuser());
    }
}
