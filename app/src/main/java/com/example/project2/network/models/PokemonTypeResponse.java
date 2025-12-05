package com.example.project2.network.models;

import com.google.gson.annotations.SerializedName;

public class PokemonTypeResponse {
    @SerializedName("type")
    private TypeResponse type;

    public TypeResponse getType() {
        return type;
    }
}
