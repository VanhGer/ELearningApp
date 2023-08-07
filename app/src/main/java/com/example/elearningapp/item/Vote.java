package com.example.elearningapp.item;

public class Vote {
    double star;
    String comment;
    String user;

    public Vote (double star, String comment, String user) {
        this.star = star;
        this.comment = comment;
        this.user = user;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
