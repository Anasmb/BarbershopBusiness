package com.example.barberbusiness.items;

public class FeedbackItem {
    private String comment;
    private int stars;

    public FeedbackItem(String comment, int stars) {
        this.comment = comment;
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public int getStars() {
        return stars;
    }
}
