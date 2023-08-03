package com.example.elearningapp.object;

import java.util.List;

public class CommentObject {
    String content;

    String id;

    int type;

    String image;

    List<String> likeList;

    public CommentObject(String content, String id, int type, String image, List<String> likeList) {
        this.content = content;
        this.id = id;
        this.type = type;
        this.image = image;
        this.likeList = likeList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<String> likeList) {
        this.likeList = likeList;
    }
}
