package com.example.sampleapp.features.domain.repository

import com.example.sampleapp.features.domain.model.BranchEntity
import com.example.sampleapp.shared.models.pagination.PaginatedResult
import com.example.sampleapp.shared.models.pagination.SearchRequest
import kotlinx.coroutines.flow.Flow

interface BranchRepository {
    suspend fun getBranches(request: SearchRequest): PaginatedResult<BranchEntity>?
    suspend fun insertBranch(branch: BranchEntity)
    suspend fun insertBranches(branches: List<BranchEntity>)
    fun getAllBranches(): Flow<List<BranchEntity>>
}
