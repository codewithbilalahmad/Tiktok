package com.muhammad.core

import android.annotation.SuppressLint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
fun String.toFormattedDate(): String {
    return try {
        val instant = Instant.parse(this)
        val formatter = DateTimeFormatter.ofPattern("MM dd, yyyy").withZone(ZoneId.systemDefault())
        formatter.format(instant)
    } catch (e: Exception) {
        this
    }
}

fun String.dropLastGrapheme(): String {
    if (this.isEmpty()) return this
    val codePoints = this.codePoints().toArray()
    if (codePoints.isEmpty()) return ""
    val newCodePoints = codePoints.dropLast(1).toIntArray()
    return String(newCodePoints, 0, newCodePoints.size)
}