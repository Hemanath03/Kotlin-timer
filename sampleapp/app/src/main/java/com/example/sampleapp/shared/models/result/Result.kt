package com.example.sampleapp.shared.models.result

data class ResponseModel<T>(
    val success: Boolean,
    val message: String?,
    val data: T?,
    val errors: List<String>?
)