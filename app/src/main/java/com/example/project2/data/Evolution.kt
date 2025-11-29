
package com.example.project2.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "evolution",
    primaryKeys = ["from_pokemon_id", "to_pokemon_id"],
    foreignKeys = [
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = ["pokemon_id"],
            childColumns = ["from_pokemon_id"]
        ),
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = ["pokemon_id"],
            childColumns = ["to_pokemon_id"]
        )
    ]
)
data class Evolution(
    val from_pokemon_id: Int,
    val to_pokemon_id: Int,
    val method: String,
    val level_required: Int
)
