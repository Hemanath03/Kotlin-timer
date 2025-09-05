package com.example.sampleapp.features.data.repository

import android.util.Log
import com.example.sampleapp.features.data.dao.BranchDao
import com.example.sampleapp.features.data.model.BranchDto
import com.example.sampleapp.features.data.remote.BranchApi
import com.example.sampleapp.features.domain.model.BranchEntity
import com.example.sampleapp.features.domain.repository.BranchRepository
import com.example.sampleapp.shared.models.pagination.PaginatedResult
import com.example.sampleapp.shared.models.pagination.SearchRequest
import com.example.sampleapp.shared.models.result.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class BranchRepositoryImpl @Inject constructor(
    private val branchApi: BranchApi,
    private val dao: BranchDao
) : BranchRepository {

    override suspend fun getBranches(request: SearchRequest): PaginatedResult<BranchEntity>? {
        return try {
            val response = branchApi.getBranches(request)
            Log.d("BranchRepositoryImpl", "Branches: $response")
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val entities = body.items.map { it.toEntity() }

                    dao.insertBranches(entities)

                    PaginatedResult(
                        items = entities,
                        totalCount = body.totalCount,
                        pageNumber = body.pageNumber,
                        totalPages = body.totalPages,
                        hasPreviousPage = body.hasPreviousPage,
                        hasNextPage = body.hasNextPage
                    )
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("BranchRepositoryImpl", "Error fetching branches", e)
            null
        }
    }

    override suspend fun insertBranch(branch: BranchEntity) {
        dao.insertBranch(branch)
    }

    override suspend fun insertBranches(branches: List<BranchEntity>) {
        dao.insertBranches(branches)
    }

    override fun getAllBranches(): Flow<List<BranchEntity>> {
        return dao.getAllBranches()
    }
}

