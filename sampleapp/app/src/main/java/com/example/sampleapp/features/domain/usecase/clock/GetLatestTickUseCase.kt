package com.example.sampleapp.features.domain.usecase.clock

import com.example.sampleapp.features.domain.model.ClockEntity
import com.example.sampleapp.features.domain.repository.ClockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestTickUseCase @Inject constructor(
    private val repository: ClockRepository
) {
    operator fun invoke(): Flow<ClockEntity?> {
        return repository.getLatestTick()
    }
}