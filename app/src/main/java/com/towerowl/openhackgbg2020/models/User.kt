package com.towerowl.openhackgbg2020.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "users")
@JsonClass(generateAdapter = true)
data class User(
    @PrimaryKey
    val username: String,
    val password: String?,
    val token: String = ""
)