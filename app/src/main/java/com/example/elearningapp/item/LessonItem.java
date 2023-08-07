package com.example.elearningapp.item;

import java.io.Serializable;

public class LessonItem implements Serializable {
    String name, des, type, script, content, image, video, lessonId, courseId;

    public LessonItem(String lessonId, String courseId, String name, String des, String type, String image, String script, String content, String video) {
        this.name = name;
        this.des = des;
        this.type = type;
        this.script = script;
        this.content = content;
        this.video = video;
        this.image = image;
        this.lessonId = lessonId;
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
