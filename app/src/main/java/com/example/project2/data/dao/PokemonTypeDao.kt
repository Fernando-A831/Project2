
package com.example.project2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.project2.data.PokemonType

@Dao
interface PokemonTypeDao {
    @Insert
    suspend fun insert(pokemonType: PokemonType)
}
