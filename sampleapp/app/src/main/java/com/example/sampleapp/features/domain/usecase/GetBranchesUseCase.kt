package com.example.sampleapp.features.domain.usecase

import com.example.sampleapp.features.domain.model.BranchEntity
import com.example.sampleapp.features.domain.repository.BranchRepository
import com.example.sampleapp.shared.models.pagination.PaginatedResult
import com.example.sampleapp.shared.models.pagination.SearchRequest

import javax.inject.Inject

class GetBranchesUseCase @Inject constructor(
    private val branchRepository: BranchRepository
) {
    suspend operator fun invoke(request: SearchRequest): PaginatedResult<BranchEntity>? {
        return branchRepository.getBranches(request)
    }
}
