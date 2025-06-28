package com.muhammad.core

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

@SuppressLint("DefaultLocale")
fun Long.formattedCount(): String {
    return when {
        this >= 1_000_000_000 -> String.format("%.1fB", this / 1_000_000_000.0)
        this >= 1_000_000 -> String.format("%.1fM", this / 1_000_000.0)
        this >= 1_000 -> String.format("%.1fK", this / 1_000.0)
        else -> this.toString()
    }
}

fun Pair<String, String>.getFormattedInternationalNumber() = "${this.first} -${this.second}".trim()

fun Context.getCurrentBrightness(): Float {
    return Settings.System.getInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS).toFloat()
        .div(255)
}