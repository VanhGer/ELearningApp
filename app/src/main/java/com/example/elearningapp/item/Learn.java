package com.example.elearningapp.item;

public class Learn {
    int learnId;
    int userId;
    int courseId;

    public Learn(int learnId, int userId, int courseId) {
        this.learnId = learnId;
        this.userId = userId;
        this.courseId = courseId;
    }

    public int getLearnId() {
        return learnId;
    }

    public void setLearnId(int learnId) {
        this.learnId = learnId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
