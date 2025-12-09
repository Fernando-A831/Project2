package com.example.project2.network.models;

import com.google.gson.annotations.SerializedName;

public class Language {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
