package com.muhammad.feature.settings

import com.muhammad.common.theme.R

data class SettingsState(
    val settingUiModel: Map<String, List<SettingItem>> = mapOf(
        "Account" to listOf(
            SettingItem(icon = R.drawable.ic_profile_fill, title = R.string.my_account)
        ),
        "Content & Display" to listOf(
            SettingItem(icon = R.drawable.ic_tv, title = R.string.live),
            SettingItem(icon = R.drawable.ic_playback, title = R.string.playback),
            SettingItem(icon = R.drawable.ic_language, title = R.string.language)
        ),
        "Cache & Cellular" to listOf(
            SettingItem(icon = R.drawable.ic_recycler_bin, title = R.string.free_up_space),
            SettingItem(icon = R.drawable.ic_data_saver, title = R.string.data_saver),
            SettingItem(icon = R.drawable.ic_wallpaper, title = R.string.wallpaper)
        ),
        "Support & About" to listOf(
            SettingItem(icon = R.drawable.ic_flag, title = R.string.report_a_problem),
            SettingItem(icon = R.drawable.ic_support, title = R.string.support),
            SettingItem(icon = R.drawable.ic_info, title = R.string.terms_and_policies)
        )
    )
)