package com.example.project2.network.models;

import com.google.gson.annotations.SerializedName;

public class Sprites {
    @SerializedName("front_default")
    private String frontDefault;

    public String getFrontDefault() {
        return frontDefault;
    }
}
