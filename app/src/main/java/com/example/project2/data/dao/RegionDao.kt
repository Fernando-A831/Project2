
package com.example.project2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.project2.data.Region

@Dao
interface RegionDao {
    @Insert
    suspend fun insert(region: Region)
}
