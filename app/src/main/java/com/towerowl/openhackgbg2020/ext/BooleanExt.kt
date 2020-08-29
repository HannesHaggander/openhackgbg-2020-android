package com.towerowl.openhackgbg2020.ext

import android.view.View

fun Boolean.asVisibility() = if (this) View.VISIBLE else View.GONE

fun Boolean.invert() = !this