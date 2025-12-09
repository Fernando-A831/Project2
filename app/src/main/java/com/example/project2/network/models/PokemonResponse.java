package com.example.project2.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("height")
    private int height;

    @SerializedName("weight")
    private int weight;

    @SerializedName("types")
    private List<PokemonTypeResponse> types;

    @SerializedName("sprites")
    private Sprites sprites;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public List<PokemonTypeResponse> getTypes() {
        return types;
    }

    public Sprites getSprites() {
        return sprites;
    }
}
