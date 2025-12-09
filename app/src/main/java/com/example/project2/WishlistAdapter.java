package com.example.project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.db.pokemon.Pokemon;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishViewHolder> {

    private final List<Pokemon> wishlist;

    public WishlistAdapter(List<Pokemon> wishlist) {
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wishlist, parent, false);
        return new WishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishViewHolder holder, int position) {
        Pokemon pokemon = wishlist.get(position);
        holder.txtName.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    static class WishViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;

        public WishViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtPokemonName);
        }
    }
}
