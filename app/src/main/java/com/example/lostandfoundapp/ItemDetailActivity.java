package com.example.lostandfoundapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView titleTypeTextView;
    private TextView dateTextView;
    private TextView locationTextView;
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView descriptionTextView;
    private Button removeButton;
    private DatabaseHelper databaseHelper;
    
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI components
        titleTypeTextView = findViewById(R.id.titleTypeTextView);
        dateTextView = findViewById(R.id.dateTextView);
        locationTextView = findViewById(R.id.locationTextView);
        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        removeButton = findViewById(R.id.removeButton);

        // Get data from intent
        if (getIntent().hasExtra("ITEM_ID")) {
            itemId = getIntent().getIntExtra("ITEM_ID", -1);
            String type = getIntent().getStringExtra("ITEM_TYPE");
            String name = getIntent().getStringExtra("ITEM_NAME");
            String phone = getIntent().getStringExtra("ITEM_PHONE");
            String description = getIntent().getStringExtra("ITEM_DESCRIPTION");
            String date = getIntent().getStringExtra("ITEM_DATE");
            String location = getIntent().getStringExtra("ITEM_LOCATION");

            // Set data to UI
            titleTypeTextView.setText(type + " " + description);
            dateTextView.setText(date);
            locationTextView.setText(location);
            nameTextView.setText("Name: " + name);
            phoneTextView.setText("Phone: " + phone);
            descriptionTextView.setText(description);

            // Set remove button click listener
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem();
                }
            });
        }
    }

    private void removeItem() {
        if (itemId != -1) {
            databaseHelper.deleteItem(itemId);
            Toast.makeText(this, "Item removed successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
} 