package com.example.CyProject.home;

import org.springframework.context.annotation.Bean;

public enum HomeCategory {
    PROFILE(1),
    DIARY(2),
    PHOTOS(3),
    VISIT(4),
    JUKEBOX(5),
    MINIROOM(6);

    private final int category;

    public int getCategory() {
        return category;
    }

    HomeCategory(int category) {
        this.category = category;
    }
}