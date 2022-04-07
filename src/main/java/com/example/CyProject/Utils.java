package com.example.CyProject;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    public int getParseIntParameter(String param) {
        int idx = 0;
        try {
            idx = Integer.parseInt(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idx;
    }
}
