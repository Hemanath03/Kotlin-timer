package com.example.sampleapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.sampleapp.features.data.dao.BranchDao
import com.example.sampleapp.features.data.dao.ClockDao
import com.example.sampleapp.features.data.remote.BranchApi
import com.example.sampleapp.features.data.repository.BranchRepositoryImpl
import com.example.sampleapp.features.data.repository.ClockRepositoryImpl
import com.example.sampleapp.features.domain.repository.BranchRepository
import com.example.sampleapp.features.domain.repository.ClockRepository
import com.example.sampleapp.features.domain.usecase.GetBranchesUseCase
import com.example.sampleapp.features.domain.usecase.clock.GetLatestTickUseCase
import com.example.sampleapp.features.domain.usecase.clock.InsertTickUseCase
import com.example.sampleapp.shared.apiClient.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideApiClient(@ApplicationContext context: Context, sharedPreferences: SharedPreferences): ApiClient {
        return ApiClient(sharedPreferences, context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(apiClient: ApiClient): Retrofit {
        return apiClient.getRetrofit()
    }



    @Provides
    @Singleton
    fun provideBranchApi(retrofit: Retrofit): BranchApi {
        return retrofit.create(BranchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBranchRepository(branchApi: BranchApi,dao: BranchDao): BranchRepository {
        return BranchRepositoryImpl(branchApi,dao)
    }

    @Provides
    @Singleton
    fun provideClockRepository(dao: ClockDao): ClockRepository {
        return ClockRepositoryImpl( dao)
    }

    @Provides
    @Singleton
    fun provideGetBranchesUseCase(branchRepository: BranchRepository): GetBranchesUseCase {
        return GetBranchesUseCase(branchRepository)
    }

    @Provides
    @Singleton
    fun provideInsertTickUseCase(repository: ClockRepository): InsertTickUseCase {
        return InsertTickUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLatestTickUseCase(repository: ClockRepository): GetLatestTickUseCase {
        return GetLatestTickUseCase(repository)
    }

}