
package com.example.project2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.project2.data.dao.*

@Database(
    entities = [
        Pokemon::class,
        Region::class,
        Type::class,
        Move::class,
        PokemonType::class,
        PokemonMove::class,
        Evolution::class
    ],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun regionDao(): RegionDao
    abstract fun typeDao(): TypeDao
    abstract fun moveDao(): MoveDao
    abstract fun pokemonTypeDao(): PokemonTypeDao
    abstract fun pokemonMoveDao(): PokemonMoveDao
    abstract fun evolutionDao(): EvolutionDao
}
