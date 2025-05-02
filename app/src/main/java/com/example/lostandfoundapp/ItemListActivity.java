package com.example.lostandfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private TextView emptyTextView;
    private List<Item> itemList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI components
        recyclerView = findViewById(R.id.itemsRecyclerView);
        emptyTextView = findViewById(R.id.emptyTextView);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(this, itemList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        // Load data
        loadItemsFromDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        loadItemsFromDatabase();
    }

    private void loadItemsFromDatabase() {
        itemList.clear();

        Cursor cursor = databaseHelper.getAllItems();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));

                Item item = new Item(id, type, name, phone, description, date, location);
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter.notifyDataSetChanged();

        // Show empty view if list is empty
        if (itemList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(Item item) {
        // Open item detail view
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra("ITEM_ID", item.getId());
        intent.putExtra("ITEM_TYPE", item.getType());
        intent.putExtra("ITEM_NAME", item.getName());
        intent.putExtra("ITEM_PHONE", item.getPhone());
        intent.putExtra("ITEM_DESCRIPTION", item.getDescription());
        intent.putExtra("ITEM_DATE", item.getDate());
        intent.putExtra("ITEM_LOCATION", item.getLocation());
        startActivity(intent);
    }

    @Override
    public void onRemoveClick(int position) {
        // Remove item from database
        Item item = itemList.get(position);
        databaseHelper.deleteItem(item.getId());

        // Remove from list and update UI
        itemList.remove(position);
        adapter.notifyItemRemoved(position);

        // Show empty view if list is empty
        if (itemList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }
} 