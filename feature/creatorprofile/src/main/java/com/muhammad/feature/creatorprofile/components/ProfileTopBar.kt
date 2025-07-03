package com.muhammad.feature.creatorprofile.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.R
import com.muhammad.common.ui.AppTopBar
import com.muhammad.core.rippleClickable

@Composable
fun ProfileTopBar(
    onBack: () -> Unit,
    fullname: String, onMoreClick: () -> Unit, onViewsClick: () -> Unit,
) {
    AppTopBar(title = fullname, actions = {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.footprint),
            contentDescription = null, modifier = Modifier
                .size(28.dp)
                .rippleClickable {
                    onMoreClick()
                }
        )
        Spacer(Modifier.width(12.dp))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger),
            contentDescription = null, modifier = Modifier
                .size(28.dp)
                .rippleClickable {
                    onViewsClick()
                }
        )
        Spacer(Modifier.width(12.dp))
    }, showNavigationIcon = true, onBack = {
        onBack()
    })
}