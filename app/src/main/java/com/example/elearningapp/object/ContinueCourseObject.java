package com.example.elearningapp.object;

public class ContinueCourseObject {
    String name;
    String image;

    int progess;

    String courseId;

    public ContinueCourseObject(String courseId, String name, String image, int progess) {
        this.name = name;
        this.image = image;
        this.progess = progess;
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getProgess() {
        return progess;
    }

    public void setProgess(int progess) {
        this.progess = progess;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
