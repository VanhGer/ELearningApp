package com.example.elearningapp.object;

public class CourseObject {
    private String name;

    private String owner;


    public CourseObject(){

    }

    public CourseObject(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
