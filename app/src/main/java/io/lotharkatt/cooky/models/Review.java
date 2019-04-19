package io.lotharkatt.cooky.models;

public class Review {
    String author;
    String feedBackMessage;
    String recipeID;

    public Review(){}

    public Review(String author, String feedBackMessage, String recipeID) {
        this.author = author;
        this.feedBackMessage = feedBackMessage;
        this.recipeID = recipeID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFeedBackMessage() {
        return feedBackMessage;
    }

    public void setFeedBackMessage(String feedBackMessage) {
        this.feedBackMessage = feedBackMessage;
    }
}
