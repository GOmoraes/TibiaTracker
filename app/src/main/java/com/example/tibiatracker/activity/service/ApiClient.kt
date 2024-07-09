package com.example.tibiatracker.activity.service

import android.content.Context
import com.example.tibiatracker.activity.utils.DateDeserializer
import com.example.tibiatracker.activity.utils.UrlApi
import com.google.gson.GsonBuilder
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


object ApiClient {
    private lateinit var mainService : MainService
    private lateinit var tibiaDataService : TibiaDataService

    private fun okhttpClient(context: Context): OkHttpClient {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.HEADERS
        }
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .apply { this.addInterceptor(interceptor) }
            .build()
    }
    class AuthenticationInterceptor(user: String, password: String) : Interceptor {
        private val credentials: String = Credentials.basic(user, password)

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build()
            return chain.proceed(authenticatedRequest)
        }
    }

    fun tibiaData(context: Context): TibiaDataService {
        if(!ApiClient::tibiaDataService.isInitialized) {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer())
            val retrofit = Retrofit.Builder()
                .baseUrl(UrlApi.BASE_URL_TIBIA_DATA)
                .client(okhttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            tibiaDataService = retrofit.create(TibiaDataService::class.java)
        }
        return tibiaDataService
    }
    fun tibiaTracker(context: Context): MainService {

        var auth = AuthenticationInterceptor("tracker", "tibiaTracker@2024")
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(auth).addInterceptor(interceptor)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS);
        var cliente = okHttpClient.build();
        cliente

        if(!ApiClient::mainService.isInitialized) {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(Date::class.java, DateDeserializer())
            val retrofit = Retrofit.Builder()
                .baseUrl(UrlApi.BASE_URL)
                .client(cliente)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mainService = retrofit.create(MainService::class.java)
        }
        return mainService
    }

}

