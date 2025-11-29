
package com.example.project2.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon",
    foreignKeys = [
        ForeignKey(
            entity = Region::class,
            parentColumns = ["region_id"],
            childColumns = ["region_id"]
        )
    ]
)
data class Pokemon(
    @PrimaryKey val pokemon_id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val region_id: Int
)
