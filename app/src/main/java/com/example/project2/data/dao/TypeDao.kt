
package com.example.project2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.project2.data.Type

@Dao
interface TypeDao {
    @Insert
    suspend fun insert(type: Type)
}
