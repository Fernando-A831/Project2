package com.example.project2.db.pokemon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.project2.db.User;

@Entity(tableName = "user_pokemon_list",
        primaryKeys = {"user_id", "pokemon_id"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Pokemon.class,
                        parentColumns = "pokemon_id",
                        childColumns = "pokemon_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class UserPokemonList {
    @ColumnInfo(name = "user_id", index = true)
    private int userId;

    @ColumnInfo(name = "pokemon_id", index = true)
    private int pokemonId;

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }
}
