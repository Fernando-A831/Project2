package com.example.project2.db.pokemon;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pokemon... pokemon);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Region... regions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Type... types);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Move... moves);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PokemonType... pokemonTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PokemonMove... pokemonMoves);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Evolution... evolutions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserPokemonList... userPokemonLists);
}
