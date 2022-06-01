package com.example.CyProject.shopping.model.item;

public enum ItemCategory {
    BGM(1),
    FONT(2),
    SKiN(3),
    MINIME(4),
    MINIROOM(5);

    private final int category;

    public int getCategory() { return category; }

    ItemCategory(int category) { this.category = category; }
}
