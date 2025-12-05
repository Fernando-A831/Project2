package com.example.project2.db.pokemon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemon",
        foreignKeys = @ForeignKey(entity = Region.class,
                                  parentColumns = "region_id",
                                  childColumns = "region_id",
                                  onDelete = ForeignKey.CASCADE))
public class Pokemon {
    @PrimaryKey
    @ColumnInfo(name = "pokemon_id")
    private int pokemonId;

    private String name;
    private int height;
    private int weight;

    @ColumnInfo(name = "region_id", index = true)
    private int regionId;

    // Getters and Setters
    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
