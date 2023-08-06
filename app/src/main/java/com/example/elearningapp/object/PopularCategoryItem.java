package com.example.elearningapp.object;

public class PopularCategoryItem {
    private String name;
    private String image;

    private String id;

    public PopularCategoryItem(String id, String name, String image) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
