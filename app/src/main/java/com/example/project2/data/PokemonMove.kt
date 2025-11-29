
package com.example.project2.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "pokemon_move",
    primaryKeys = ["pokemon_id", "move_id"],
    foreignKeys = [
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = ["pokemon_id"],
            childColumns = ["pokemon_id"]
        ),
        ForeignKey(
            entity = Move::class,
            parentColumns = ["move_id"],
            childColumns = ["move_id"]
        )
    ]
)
data class PokemonMove(
    val pokemon_id: Int,
    val move_id: Int
)
