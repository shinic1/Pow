package com.example.pow;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RateSkiResortActivity extends AppCompatActivity {
    private String resortName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_ski_resort);

        TextView ratingTextView = findViewById(R.id.ratingTextView);
        EditText ratingEditText = findViewById(R.id.ratingEditText);
        Button submitRatingButton = findViewById(R.id.submitRatingButton);

        // Get the resort name
        resortName = getIntent().getStringExtra("RESORT_NAME");
        ratingTextView.setText("Rate " + resortName);

        // Show the average rating
        double averageRating = RatingHelper.calculateAverageRating(this, resortName);
        String formattedAverageRating = String.format("%.2f", averageRating);
        Toast.makeText(this, "Current Average Rating: " + formattedAverageRating, Toast.LENGTH_LONG).show();

        submitRatingButton.setOnClickListener(v -> {
            String ratingStr = ratingEditText.getText().toString();

            // Validate the rating input
            if (ratingStr.isEmpty()) {
                Toast.makeText(RateSkiResortActivity.this, "Please enter a rating.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int rating = Integer.parseInt(ratingStr);

                // Check if the rating is within the range 1 to 5
                if (rating >= 1 && rating <= 5) {
                    RatingHelper.saveRating(this, resortName, rating);

                    // Send the new average rating back to the previous activity
                    double newAverage = RatingHelper.calculateAverageRating(this, resortName);
                    String formattedNewAverage = String.format("%.2f", newAverage);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("RESORT_AVERAGE_RATING", formattedNewAverage);
                    setResult(RESULT_OK, resultIntent);

                    // Close this activity and return to the description screen
                    finish();
                } else {
                    Toast.makeText(RateSkiResortActivity.this, "Rating must be between 1 and 5.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(RateSkiResortActivity.this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}