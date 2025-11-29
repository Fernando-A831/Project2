
package com.example.project2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.project2.data.Pokemon
import com.example.project2.data.PokemonWithMoves
import com.example.project2.data.PokemonWithTypes

@Dao
interface PokemonDao {
    @Insert
    suspend fun insert(pokemon: Pokemon)

    @Transaction
    @Query("SELECT * FROM pokemon")
    fun getPokemonWithTypes(): List<PokemonWithTypes>

    @Transaction
    @Query("SELECT * FROM pokemon")
    fun getPokemonWithMoves(): List<PokemonWithMoves>
}
