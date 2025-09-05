package com.example.sampleapp.features.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clock_table")
data class ClockEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long
)
