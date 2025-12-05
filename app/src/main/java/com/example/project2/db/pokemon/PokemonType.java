package com.example.project2.db.pokemon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "pokemon_type",
        primaryKeys = {"pokemon_id", "type_id"},
        foreignKeys = {
                @ForeignKey(entity = Pokemon.class,
                            parentColumns = "pokemon_id",
                            childColumns = "pokemon_id",
                            onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Type.class,
                            parentColumns = "type_id",
                            childColumns = "type_id",
                            onDelete = ForeignKey.CASCADE)
        })
public class PokemonType {
    @ColumnInfo(name = "pokemon_id")
    private int pokemonId;

    @ColumnInfo(name = "type_id", index = true)
    private int typeId;

    // Getters and Setters
    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
