package com.example.elearningapp.object;

public class CourseListItem {

    private String id;
    private String image;

    private String name;

    private String owner;

    private String description;

    private int numberStudent;

    private double numberStar;


    public CourseListItem(String id, String image, String name, String owner, String description, int numberStudent, double numberStar) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.numberStudent = numberStudent;
        this.description = description;
        this.numberStar = numberStar;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getNumberStudent() {
        return numberStudent;
    }

    public double getNumberStar() {
        return numberStar;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberStudent(int numberStudent) {
        this.numberStudent = numberStudent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setNumberStar(double numberStar) {
        this.numberStar = numberStar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
