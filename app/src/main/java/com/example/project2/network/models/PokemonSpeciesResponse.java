package com.example.project2.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonSpeciesResponse {
    @SerializedName("flavor_text_entries")
    private List<FlavorTextEntry> flavorTextEntries;

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }
}
