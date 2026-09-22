package com.millie.financemanager.dto;

public class CategoryRequestDto {

    private String name;
    private String color;

    public CategoryRequestDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
