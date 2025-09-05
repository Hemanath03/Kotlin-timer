package com.example.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.example.sampleapp.database.AppDatabase
import com.example.sampleapp.features.data.dao.BranchDao
import com.example.sampleapp.features.data.dao.ClockDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "myCourier_db"
            ).fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideBranchDao(db: AppDatabase): BranchDao = db.branchDao()

    @Provides
    fun provideClockDao(db: AppDatabase): ClockDao = db.clockDao()
}
