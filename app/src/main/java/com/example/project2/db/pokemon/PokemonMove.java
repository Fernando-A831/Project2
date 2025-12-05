package com.example.project2.db.pokemon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "pokemon_move",
        primaryKeys = {"pokemon_id", "move_id"},
        foreignKeys = {
                @ForeignKey(entity = Pokemon.class,
                            parentColumns = "pokemon_id",
                            childColumns = "pokemon_id",
                            onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Move.class,
                            parentColumns = "move_id",
                            childColumns = "move_id",
                            onDelete = ForeignKey.CASCADE)
        })
public class PokemonMove {
    @ColumnInfo(name = "pokemon_id")
    private int pokemonId;

    @ColumnInfo(name = "move_id", index = true)
    private int moveId;

    // Getters and Setters
    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public int getMoveId() {
        return moveId;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }
}
