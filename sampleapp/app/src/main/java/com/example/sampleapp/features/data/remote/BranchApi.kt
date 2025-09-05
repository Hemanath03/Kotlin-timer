package com.example.sampleapp.features.data.remote

import com.example.sampleapp.features.data.model.BranchDto
import com.example.sampleapp.shared.models.pagination.PaginatedResult
import com.example.sampleapp.shared.models.pagination.SearchRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.CompletableFuture

interface BranchApi {

    @POST("api/Branches/pagination-list")
    suspend fun getBranches(
        @Body request: SearchRequest
    ): Response<PaginatedResult<BranchDto>>
}
