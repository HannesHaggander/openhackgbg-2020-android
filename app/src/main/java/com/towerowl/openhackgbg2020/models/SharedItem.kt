package com.towerowl.openhackgbg2020.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class SharedItem(
    val id: UUID,
    val name: String,
    var available: Int,
    @DrawableRes
    var imageResId: Int
) : Parcelable {
    fun isAvailable(): Boolean = available > 0

    fun claim() {
        available = if (isAvailable()) 0 else 1
    }
}