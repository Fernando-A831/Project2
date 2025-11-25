
package com.example.project2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type")
data class Type(
    @PrimaryKey val type_id: Int,
    val name: String
)
