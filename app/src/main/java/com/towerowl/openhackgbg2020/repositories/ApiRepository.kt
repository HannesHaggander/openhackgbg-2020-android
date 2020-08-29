package com.towerowl.openhackgbg2020.repositories

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.towerowl.openhackgbg2020.models.Community
import com.towerowl.openhackgbg2020.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

class ApiRepository {

    companion object {
        const val BASE_URL = "https://localhost:8000/api/v1/"
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).build()
    }

    val authentication by lazy { retrofit.create(Authentication::class.java) }
    val communities by lazy { retrofit.create(Communities::class.java) }
    val items by lazy { retrofit.create(Items::class.java) }

}

interface Authentication {
    @Headers("Content-Type: application/json")
    @PUT("login")
    fun login(@Body user: User): Call<User>
}

interface Communities {
    @GET("getAllCommunities")
    fun getAll(): Call<List<Community>>
}

interface Items {

}