package com.example.project2.db.pokemon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemon")
public class Pokemon {
    @PrimaryKey
    @ColumnInfo(name = "pokemon_id")
    private int pokemonId;

    private String name;
    private int height;
    private int weight;
    private String spriteUrl;
    private String pokedexEntry;

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

    public String getSpriteUrl() {
        return spriteUrl;
    }

    public void setSpriteUrl(String spriteUrl) {
        this.spriteUrl = spriteUrl;
    }

    public String getPokedexEntry() {
        return pokedexEntry;
    }

    public void setPokedexEntry(String pokedexEntry) {
        this.pokedexEntry = pokedexEntry;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
