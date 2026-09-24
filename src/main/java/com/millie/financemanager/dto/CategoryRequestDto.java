package com.millie.financemanager.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {

    @NotBlank
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
