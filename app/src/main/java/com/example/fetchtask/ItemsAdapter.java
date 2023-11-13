package com.example.fetchtask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for RecyclerView.
 * This adapter binds the data from a list of Item objects to views that are displayed within a RecyclerView.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    private List<Item> items;

    /**
     * Constructor for ItemsAdapter.
     *
     * @param items The list of items to be displayed in the RecyclerView.
     */
    public ItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.textView.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * ViewHolder for items in the RecyclerView.
     * This class holds the views for each item in the RecyclerView, enabling efficient element reuse.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        /**
         * Constructor for ViewHolder.
         *
         * @param itemView The view representing a single item in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);

        }
    }
}
