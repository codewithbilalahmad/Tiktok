package com.muhammad.feature.creatorprofile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.SeparatorColor
import com.muhammad.common.theme.SubTextColor
import com.muhammad.feature.creatorprofile.ProfileTabs
import kotlinx.coroutines.launch
import kotlin.enums.EnumEntries

@Composable
fun ProfileTabSection(pagerState: PagerState, pages: EnumEntries<ProfileTabs>) {
    val scope = rememberCoroutineScope()
    TabRow(selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
        val modifier =
            Modifier
                .clip(CircleShape)
                .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                .padding(horizontal = 26.dp)
        TabRowDefaults.PrimaryIndicator(modifier = modifier)
    }, divider = {
        HorizontalDivider(thickness = 0.5.dp, color = SeparatorColor)
    }) {
        pages.forEachIndexed { index, item ->
            val isSelected = pagerState.currentPage == index
            Tab(
                selected = isSelected,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = SubTextColor,
                text = {
                    val tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = null,
                        tint = tint
                    )
                })
        }
    }
}