package com.muhammad.core

import android.content.Context
import android.provider.Settings

fun Long.formattedCount(): String {
    return when {
        this >= 1_000_000_000 -> ".1fB".format(this / 1_000_000_000)
        this >= 1_000_000 -> ".1fM".format(this / 1_000_000)
        this >= 1_000 -> ".1fK".format(this / 1_000)
        else -> this.toString()
    }
}

fun Pair<String, String>.getFormattedInternationalNumber() = "${this.first} -${this.second}".trim()

fun Context.getCurrentBrightness(): Float {
    return Settings.System.getInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS).toFloat()
        .div(255)
}