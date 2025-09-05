package com.example.sampleapp.features.data.repository

import com.example.sampleapp.features.data.dao.ClockDao
import com.example.sampleapp.features.domain.model.ClockEntity
import com.example.sampleapp.features.domain.repository.ClockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClockRepositoryImpl  @Inject constructor(private val dao: ClockDao): ClockRepository{
    override suspend fun insertTick(clock: ClockEntity) = dao.insertTick(clock)

    override fun getLatestTick(): Flow<ClockEntity?> = dao.getLatestTick()
}