package com.example.pow;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RatingHelper {
    // Save a rating for a specific resort
    public static void saveRating(Context context, String resortName, int rating) {
        String fileName = resortName + "_ratings.txt";
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(rating + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Calculate the average rating for a specific resort
    public static double calculateAverageRating(Context context, String resortName) {
        List<Integer> ratings = readRatingsFromFile(context, resortName);
        if (ratings.isEmpty()) return 0.0;

        double total = 0;
        for (int rating : ratings) {
            total += rating;
        }
        return total / ratings.size();
    }

    // Read all ratings from the file for a specific resort
    public static List<Integer> readRatingsFromFile(Context context, String resortName) {
        List<Integer> ratings = new ArrayList<>();
        String fileName = resortName + "_ratings.txt";

        try (FileInputStream fis = context.openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    ratings.add(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            // File not found handling
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ratings;
    }
}
