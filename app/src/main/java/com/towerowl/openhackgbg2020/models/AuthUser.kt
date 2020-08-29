package com.towerowl.openhackgbg2020.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthUser(
    val username: String,
    val password: String
)