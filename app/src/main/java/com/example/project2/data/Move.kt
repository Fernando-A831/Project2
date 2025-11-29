
package com.example.project2.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "move",
    foreignKeys = [
        ForeignKey(
            entity = Type::class,
            parentColumns = ["type_id"],
            childColumns = ["type_id"]
        )
    ]
)
data class Move(
    @PrimaryKey val move_id: Int,
    val name: String,
    val power: Int,
    val type_id: Int
)
