
package com.example.project2.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PokemonWithMoves(
    @Embedded val pokemon: Pokemon,
    @Relation(
        parentColumn = "pokemon_id",
        entityColumn = "move_id",
        associateBy = Junction(PokemonMove::class)
    )
    val moves: List<Move>
)
