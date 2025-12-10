package com.example.project2.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "team_members")
public class TeamMember {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String imageUrl;
    private String types;

    public TeamMember(String name, String imageUrl, String types) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.types = types;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}