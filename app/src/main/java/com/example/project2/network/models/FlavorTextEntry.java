package com.example.project2.network.models;

import com.google.gson.annotations.SerializedName;

public class FlavorTextEntry {
    @SerializedName("flavor_text")
    private String flavorText;

    @SerializedName("language")
    private Language language;

    public String getFlavorText() {
        return flavorText;
    }

    public Language getLanguage() {
        return language;
    }
}
