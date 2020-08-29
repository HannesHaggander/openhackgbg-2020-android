package com.towerowl.openhackgbg2020.net

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Api {

    companion object {
        const val BASE_URL = "baseurl"
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }

    val authentication by lazy { retrofit.create(Authentication::class.java) }
    val communities by lazy { retrofit.create(Communities::class.java) }
    val items by lazy { retrofit.create(Items::class.java) }

}

interface Authentication {

}

interface Communities {

}

interface Items {

}