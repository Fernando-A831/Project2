package com.example.project2.network.models;

import com.google.gson.annotations.SerializedName;

public class PokemonListResponse {
    @SerializedName("count")
    private int count;

    public int getCount() {
        return count;
    }
}
