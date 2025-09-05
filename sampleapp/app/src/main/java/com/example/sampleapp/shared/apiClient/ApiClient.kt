package com.example.sampleapp.shared.apiClient

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.sampleapp.shared.constants.Constants
import com.example.sampleapp.shared.constants.Constants.Access_Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) {
    private val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttp = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val token = sharedPreferences.getString("token", null)
                val req = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .apply {
                        //if (!token.isNullOrEmpty())
                            addHeader("Authorization", Access_Token)
                    }
                    .build()
                chain.proceed(req)
            }
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofit(): Retrofit = retrofit
}
