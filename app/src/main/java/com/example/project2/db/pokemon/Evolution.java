package com.example.project2.db.pokemon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "evolution",
        primaryKeys = {"from_pokemon_id", "to_pokemon_id"},
        foreignKeys = {
                @ForeignKey(entity = Pokemon.class,
                            parentColumns = "pokemon_id",
                            childColumns = "from_pokemon_id",
                            onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Pokemon.class,
                            parentColumns = "pokemon_id",
                            childColumns = "to_pokemon_id",
                            onDelete = ForeignKey.CASCADE)
        })
public class Evolution {
    @ColumnInfo(name = "from_pokemon_id")
    private int fromPokemonId;

    @ColumnInfo(name = "to_pokemon_id", index = true)
    private int toPokemonId;

    private String method;

    @ColumnInfo(name = "level_required")
    private int levelRequired;

    // Getters and Setters
    public int getFromPokemonId() {
        return fromPokemonId;
    }

    public void setFromPokemonId(int fromPokemonId) {
        this.fromPokemonId = fromPokemonId;
    }

    public int getToPokemonId() {
        return toPokemonId;
    }

    public void setToPokemonId(int toPokemonId) {
        this.toPokemonId = toPokemonId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public void setLevelRequired(int levelRequired) {
        this.levelRequired = levelRequired;
    }
}
