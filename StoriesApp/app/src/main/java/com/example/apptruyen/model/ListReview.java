package com.example.apptruyen.model;

import java.util.ArrayList;

public class ListReview {

    public  boolean status;
    public  String message;
    ArrayList<Review> review;

    public ArrayList<Review> getReview() {
        return review;
    }

    public void setReview(ArrayList<Review> review) {
        this.review = review;
    }
}
