package com.example.elearningapp.object;

public class PopularCategoryItem {
    private String name;
    private String image;

    public PopularCategoryItem(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
