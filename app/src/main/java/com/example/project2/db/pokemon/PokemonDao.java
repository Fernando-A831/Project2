package com.example.project2.db.pokemon;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pokemon... pokemon);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Type type);

    @Query("SELECT * FROM `type` WHERE name = :name")
    Type getTypeByName(String name);

    @Query("SELECT * FROM pokemon WHERE pokemon_id = :pokemonId")
    Pokemon getPokemonById(int pokemonId);

    @Query("SELECT * FROM pokemon ORDER BY RANDOM() LIMIT 1")
    Pokemon getRandomPokemon();

    @Query("SELECT T.name FROM `type` T JOIN pokemon_type PT ON T.type_id = PT.type_id WHERE PT.pokemon_id = :pokemonId")
    List<String> getTypesForPokemon(int pokemonId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Region... regions);

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

    @Query("SELECT * FROM pokemon ORDER BY name ASC")
    List<Pokemon> getAllPokemon();

}
