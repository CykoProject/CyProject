package com.example.CyProject.main.model.top;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopService {

    public List<TopVo> toListVisitorVo(List<TopHelper> list) {
        List<TopVo> result = new ArrayList<>();

        for(TopHelper item : list) {
            result.add(item.toVisitorVo());
        }

        return result;
    }
}
