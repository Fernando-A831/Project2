
package com.example.project2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.project2.data.Move

@Dao
interface MoveDao {
    @Insert
    suspend fun insert(move: Move)
}
