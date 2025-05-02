package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button createAdvertButton;
    private Button showItemsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Initialize UI components
        createAdvertButton = findViewById(R.id.createAdvertButton);
        showItemsButton = findViewById(R.id.showItemsButton);
        
        // Set click listeners for buttons
        createAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAdvertActivity();
            }
        });
        
        showItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemListActivity();
            }
        });
        
        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    private void openCreateAdvertActivity() {
        Intent intent = new Intent(this, CreateAdvertActivity.class);
        startActivity(intent);
    }
    
    private void openItemListActivity() {
        Intent intent = new Intent(this, ItemListActivity.class);
        startActivity(intent);
    }
}