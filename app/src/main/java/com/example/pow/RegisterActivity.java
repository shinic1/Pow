package com.example.pow;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class RegisterActivity extends AppCompatActivity {

    private static final String FILE_NAME = "user_credentials.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            //Input validation
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            //Sends notification if registration was successful
            saveCredentials(username, password);
            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish(); // Close this activity and return to LoginActivity
        });
    }

    private void saveCredentials(String username, String password) {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(username + ":" + password + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
