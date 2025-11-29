
package com.example.project2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.project2.data.PokemonMove

@Dao
interface PokemonMoveDao {
    @Insert
    suspend fun insert(pokemonMove: PokemonMove)
}
