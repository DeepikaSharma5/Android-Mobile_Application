package com.example.myapplication1;

public class ModelFeedback {

    private String username;
    private String feedback;

    public ModelFeedback() {
    }

    public ModelFeedback(String username, String feedback) {
        this.username = username;
        this.feedback = feedback;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
