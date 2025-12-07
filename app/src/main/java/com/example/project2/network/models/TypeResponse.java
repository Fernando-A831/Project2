package com.example.project2.network.models;

import com.google.gson.annotations.SerializedName;

public class TypeResponse {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
