package com.example.elearningapp.object;

public class CourseListItem {
    private String image;

    private String name;

    private String owner;

    private int numberStudent;

    private double numberStar;


    public CourseListItem(String image, String name, String owner, int numberStudent, double numberStar) {
        this.image = image;
        this.name = name;
        this.numberStudent = numberStudent;
        this.numberStar = numberStar;
        this.owner = owner;
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
}
