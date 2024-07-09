package com.example.tibiatracker.activity.service

import android.content.Context
import com.example.tibiatracker.activity.utils.DateDeserializer
import com.example.tibiatracker.activity.utils.UrlApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object ApiClient {
    private lateinit var mainService : MainService

    private fun okhttpClient(context: Context): OkHttpClient {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.HEADERS
        }
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .apply { this.addInterceptor(interceptor) }
            .build()
    }

    fun tibiaData(context: Context): MainService {
        if(!ApiClient::mainService.isInitialized) {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer())
            val retrofit = Retrofit.Builder()
                .baseUrl(UrlApi.BASE_URL_TIBIA_DATA)
                .client(okhttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mainService = retrofit.create(MainService::class.java)
        }
        return mainService
    }
    fun tibiaTracker(context: Context): MainService {
        if(!ApiClient::mainService.isInitialized) {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer())
            val retrofit = Retrofit.Builder()
                .baseUrl(UrlApi.BASE_URL)
                .client(okhttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mainService = retrofit.create(MainService::class.java)
        }
        return mainService
    }

}

