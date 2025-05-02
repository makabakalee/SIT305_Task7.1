package com.example.lostandfoundapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {

    private RadioGroup postTypeRadioGroup;
    private RadioButton lostRadioButton;
    private RadioButton foundRadioButton;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText descriptionEditText;
    private EditText dateEditText;
    private EditText locationEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI components
        postTypeRadioGroup = findViewById(R.id.postTypeRadioGroup);
        lostRadioButton = findViewById(R.id.lostRadioButton);
        foundRadioButton = findViewById(R.id.foundRadioButton);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        locationEditText = findViewById(R.id.locationEditText);
        saveButton = findViewById(R.id.saveButton);

        // Default selection
        lostRadioButton.setChecked(true);

        // Set up save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        // Get values from form
        String type = lostRadioButton.isChecked() ? "Lost" : "Found";
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(description) 
                || TextUtils.isEmpty(date) || TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to database
        long id = databaseHelper.addItem(type, name, phone, description, date, location);

        if (id != -1) {
            Toast.makeText(this, "Item saved successfully", Toast.LENGTH_SHORT).show();
            // Clear the form
            clearForm();
            // Return to previous screen
            finish();
        } else {
            Toast.makeText(this, "Error saving item", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        nameEditText.setText("");
        phoneEditText.setText("");
        descriptionEditText.setText("");
        dateEditText.setText("");
        locationEditText.setText("");
        lostRadioButton.setChecked(true);
    }
} 