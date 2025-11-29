
package com.example.project2.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PokemonWithTypes(
    @Embedded val pokemon: Pokemon,
    @Relation(
        parentColumn = "pokemon_id",
        entityColumn = "type_id",
        associateBy = Junction(PokemonType::class)
    )
    val types: List<Type>
)
