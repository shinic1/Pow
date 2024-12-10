package com.example.pow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SkiResortDetailsActivity extends AppCompatActivity {
    private static final int RATING_REQUEST_CODE = 1;
    private TextView ratingTextView;
    private String resortName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ski_resort_details);

        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView locationTextView = findViewById(R.id.locationTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        Button rateButton = findViewById(R.id.rateButton);

        // Get data from intent
        resortName = getIntent().getStringExtra("RESORT_NAME");
        String resortLocation = getIntent().getStringExtra("RESORT_LOCATION");
        String resortDescription = getIntent().getStringExtra("RESORT_DESCRIPTION");

        nameTextView.setText(resortName);
        locationTextView.setText(resortLocation);
        descriptionTextView.setText(resortDescription);

        // Display the average rating
        updateAverageRating();

        rateButton.setOnClickListener(v -> {
            Intent intent = new Intent(SkiResortDetailsActivity.this, RateSkiResortActivity.class);
            intent.putExtra("RESORT_NAME", resortName);
            startActivityForResult(intent, RATING_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RATING_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the updated average rating
            String updatedRating = data.getStringExtra("RESORT_AVERAGE_RATING");
            if (updatedRating != null) {
                ratingTextView.setText("Average Rating: " + updatedRating);
            }
        }
    }


    //Updates rating when more than one is registered
    private void updateAverageRating() {
        double averageRating = RatingHelper.calculateAverageRating(this, resortName);
        if (averageRating > 0) {
            String formattedRating = String.format("%.2f", averageRating);
            ratingTextView.setText("Average Rating: " + formattedRating);
        } else {
            ratingTextView.setText("Rating: Not rated");
        }
    }
}