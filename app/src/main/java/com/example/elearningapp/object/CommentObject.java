package com.example.elearningapp.object;

import java.util.List;

public class CommentObject {
    String content;

    String id;

    int type;

    String userId;

    List<String> likeList;

    Long timestamp;

    public CommentObject(String content, String id, int type, String userId, List<String> likeList, Long timestamp) {
        this.content = content;
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.likeList = likeList;
        this.timestamp = timestamp;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String image) {
        this.userId = image;
    }

    public List<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<String> likeList) {
        this.likeList = likeList;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
