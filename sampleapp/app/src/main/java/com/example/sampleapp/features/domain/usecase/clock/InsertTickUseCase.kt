package com.example.sampleapp.features.domain.usecase.clock

import com.example.sampleapp.features.domain.model.ClockEntity
import com.example.sampleapp.features.domain.repository.ClockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertTickUseCase @Inject constructor(private val repository: ClockRepository) {
    suspend operator fun invoke(tick: ClockEntity) {
        withContext(Dispatchers.IO) {
            repository.insertTick(tick)
        }
    }
}

