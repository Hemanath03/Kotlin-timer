package com.example.sampleapp.shared.models.pagination

data class SearchRequest(
    val pageNumber: Int,
    val pageSize: Int,
    val search: String? = null,
    val searchColumn: String? = null,
    val sortColumnName: String? = null,
    val sortDirection: String? = null
)

