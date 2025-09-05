package com.example.sampleapp.features.domain.repository

import com.example.sampleapp.features.domain.model.ClockEntity
import kotlinx.coroutines.flow.Flow

interface ClockRepository {
    suspend fun insertTick(clock: ClockEntity)
    fun getLatestTick(): Flow<ClockEntity?>
}