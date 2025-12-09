package com.example.project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.db.pokemon.Pokemon;

import java.util.List;

public class GenerationSearchAdapter extends RecyclerView.Adapter<GenerationSearchAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemonList;
    private OnPokemonClickListener onPokemonClickListener;

    public GenerationSearchAdapter(List<Pokemon> pokemonList, OnPokemonClickListener onPokemonClickListener) {
        this.pokemonList = pokemonList;
        this.onPokemonClickListener = onPokemonClickListener;
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon, onPokemonClickListener);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        private ImageView pokemonImage;
        private TextView pokemonName;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.pokemonItemImage);
            pokemonName = itemView.findViewById(R.id.pokemonItemName);
        }

        public void bind(Pokemon pokemon, OnPokemonClickListener listener) {
            pokemonName.setText(pokemon.getName());
            Glide.with(itemView.getContext())
                    .load(pokemon.getSpriteUrl())
                    .into(pokemonImage);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPokemonClick(pokemon);
                }
            });
        }
    }

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon);
    }
}
