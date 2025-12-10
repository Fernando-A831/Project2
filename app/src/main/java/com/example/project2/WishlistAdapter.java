package com.example.project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.db.WishlistItem;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private List<WishlistItem> wishlistItems;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(WishlistItem item);
    }

    public WishlistAdapter(List<WishlistItem> wishlistItems, OnDeleteClickListener deleteListener) {
        this.wishlistItems = wishlistItems;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wishlist, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        WishlistItem item = wishlistItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return wishlistItems.size();
    }

    class WishlistViewHolder extends RecyclerView.ViewHolder {
        private ImageView pokemonImage;
        private TextView pokemonName;
        private TextView pokemonTypes;
        private ImageButton deleteButton;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.wishlistItemImage);
            pokemonName = itemView.findViewById(R.id.wishlistItemName);
            pokemonTypes = itemView.findViewById(R.id.wishlistItemTypes);
            deleteButton = itemView.findViewById(R.id.wishlistDeleteButton);
        }

        public void bind(WishlistItem item) {
            // Capitalize first letter
            String name = item.getName();
            if (name != null && !name.isEmpty()) {
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
            }
            pokemonName.setText(name);
            pokemonTypes.setText(item.getTypes());

            // Load image with Glide
            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(pokemonImage);

            // Handle delete button click
            deleteButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(item);
                }
            });
        }
    }
}