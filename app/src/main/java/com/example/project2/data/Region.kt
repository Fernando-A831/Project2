
package com.example.project2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "region")
data class Region(
    @PrimaryKey val region_id: Int,
    val name: String
)
