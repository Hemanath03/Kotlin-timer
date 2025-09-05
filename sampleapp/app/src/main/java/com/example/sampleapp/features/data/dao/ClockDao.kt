package com.example.sampleapp.features.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapp.features.domain.model.ClockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTick(clock: ClockEntity)

    @Query("SELECT * FROM clock_table ORDER BY id DESC LIMIT 1")
    fun getLatestTick(): Flow<ClockEntity?>
}
