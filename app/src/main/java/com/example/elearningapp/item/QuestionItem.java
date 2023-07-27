package com.example.elearningapp.item;

public class QuestionItem {
    int answerId;
    String questionId, lessonId, content, choice1, choice2, choice3, choice4;

    public QuestionItem(String questionId, String lessonId, String content, String choice1, String choice2, String choice3, String choice4, int answerId) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.lessonId = lessonId;
        this.content = content;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getAnswer() {
        switch (answerId){
            case 1:
                return choice1;
            case 2:
                return choice2;
            case 3:
                return choice3;
            case 4:
                return choice4;
        }
        return "No data";
    }
}
