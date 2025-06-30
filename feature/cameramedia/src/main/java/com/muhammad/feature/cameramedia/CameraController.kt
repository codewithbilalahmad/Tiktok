package com.muhammad.feature.cameramedia

import androidx.annotation.*
import com.muhammad.common.theme.R

enum class CameraController(
    @get:StringRes val title: Int,
    @get:DrawableRes val icon: Int
) {
    FLIP(title = R.string.flip, icon = R.drawable.ic_flip),
    SPEED(title = R.string.speed, icon = R.drawable.ic_speed),
    BEAUTY(title = R.string.beauty, icon = R.drawable.ic_profile_fill),
    FILTER(title = R.string.filters, icon = R.drawable.ic_filter),
    MIRROR(title = R.string.mirror, icon = R.drawable.ic_mirror),
    TIMER(title = R.string.timer, icon = R.drawable.ic_timer),
    FLASH(title = R.string.flash, icon = R.drawable.ic_flash),
}