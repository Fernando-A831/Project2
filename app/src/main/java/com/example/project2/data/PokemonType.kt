
package com.example.project2.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "pokemon_type",
    primaryKeys = ["pokemon_id", "type_id"],
    foreignKeys = [
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = ["pokemon_id"],
            childColumns = ["pokemon_id"]
        ),
        ForeignKey(
            entity = Type::class,
            parentColumns = ["type_id"],
            childColumns = ["type_id"]
        )
    ]
)
data class PokemonType(
    val pokemon_id: Int,
    val type_id: Int
)
