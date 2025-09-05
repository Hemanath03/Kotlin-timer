package com.example.sampleapp.shared.models.pagination

data class PaginatedResult<T>(
    val items: List<T>,
    val pageNumber: Int,
    val totalPages: Int,
    val totalCount: Int,
    val hasPreviousPage: Boolean,
    val hasNextPage: Boolean
)
