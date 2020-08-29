package com.towerowl.openhackgbg2020.repositories

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.towerowl.openhackgbg2020.models.ApiStatusResponse
import com.towerowl.openhackgbg2020.models.AuthUser
import com.towerowl.openhackgbg2020.models.Community
import com.towerowl.openhackgbg2020.models.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class ApiRepository {

    companion object {
        const val BASE_URL = "http://85.228.187.157:8000/api/v1/"
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).build()
    }

    private fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response = chain.request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build()
                .run { chain.proceed(this) }
        }).build()

    val authentication by lazy { retrofit.create(Authentication::class.java) }
    val communities by lazy { retrofit.create(Communities::class.java) }
    val items by lazy { retrofit.create(Items::class.java) }

}

interface Authentication {
    @POST("register")
    fun register(@Body authUser: AuthUser): Call<ApiStatusResponse>

    @GET("login")
    fun login(@Body authUser: AuthUser): Call<User>
}

interface Communities {
    @GET("getAllCommunities")
    fun getAll(): Call<List<Community>>
}

interface Items {

}