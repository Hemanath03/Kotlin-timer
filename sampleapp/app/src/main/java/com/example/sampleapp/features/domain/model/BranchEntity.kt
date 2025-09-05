package com.example.sampleapp.features.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "branches")
data class BranchEntity(
    @PrimaryKey
    val branchId: String,
    val name: String,
    val code: String,
    val description: String?,
    val email: String?,
    val telephone: String?,
    val mobile: String?
)
