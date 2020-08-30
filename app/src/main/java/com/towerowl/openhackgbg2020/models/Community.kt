package com.towerowl.openhackgbg2020.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Community(
    val id: UUID,
    val name: String
) : Parcelable