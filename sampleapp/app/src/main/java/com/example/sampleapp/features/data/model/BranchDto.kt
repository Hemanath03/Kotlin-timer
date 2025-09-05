package com.example.sampleapp.features.data.model

import com.example.sampleapp.features.domain.model.BranchEntity

data class BranchDto(
    val branchId: String,
    val name: String,
    val code: String,
    val description: String?,
    val email: String?,
    val telephone: String?,
    val mobile: String?
) {
    fun toEntity() = BranchEntity(
        branchId = branchId,
        name = name,
        code = code,
        description = description,
        email = email,
        telephone = telephone,
        mobile = mobile
    )
}
