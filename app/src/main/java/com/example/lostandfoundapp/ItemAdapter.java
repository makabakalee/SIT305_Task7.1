package com.example.lostandfoundapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final Context context;
    private final List<Item> itemList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lost_found, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        // Set item type with appropriate color
        holder.itemTypeTextView.setText(item.getType());
        if (item.getType().equals("Lost")) {
            holder.itemTypeTextView.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            holder.itemTypeTextView.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }

        // Set item details
        holder.itemDescriptionTextView.setText(item.getDescription());
        holder.itemLocationTextView.setText(item.getLocation());
        holder.itemDateTextView.setText(item.getDate());
        holder.itemContactTextView.setText("Contact: " + item.getName() + " (" + item.getPhone() + ")");

        // Set click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

        holder.removeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemoveClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTypeTextView;
        TextView itemDescriptionTextView;
        TextView itemLocationTextView;
        TextView itemDateTextView;
        TextView itemContactTextView;
        Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTypeTextView = itemView.findViewById(R.id.itemTypeTextView);
            itemDescriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);
            itemLocationTextView = itemView.findViewById(R.id.itemLocationTextView);
            itemDateTextView = itemView.findViewById(R.id.itemDateTextView);
            itemContactTextView = itemView.findViewById(R.id.itemContactTextView);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
} 