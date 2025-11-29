
package com.example.project2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.project2.data.Evolution

@Dao
interface EvolutionDao {
    @Insert
    suspend fun insert(evolution: Evolution)
}
