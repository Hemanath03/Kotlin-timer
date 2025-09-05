package com.example.sampleapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sampleapp.features.data.dao.BranchDao
import com.example.sampleapp.features.data.dao.ClockDao
import com.example.sampleapp.features.domain.model.BranchEntity
import com.example.sampleapp.features.domain.model.ClockEntity

@Database(entities = [BranchEntity::class, ClockEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun branchDao(): BranchDao
    abstract fun clockDao(): ClockDao
}
