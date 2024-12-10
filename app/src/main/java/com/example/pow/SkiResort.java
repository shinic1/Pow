package com.example.pow;

import java.util.ArrayList;
import java.util.List;

public class SkiResort {
    private String name;
    private String location;
    private String description;
    private List<Integer> ratings;

    public SkiResort(String name, String location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.ratings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public void addRating(int rating) {
        ratings.add(rating);
    }

    //Calculate average for rating
    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        double sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return sum / ratings.size();
    }

    public List<Integer> getRatings() {
        return ratings;
    }
}
