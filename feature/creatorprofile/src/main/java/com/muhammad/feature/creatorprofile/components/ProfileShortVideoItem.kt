package com.muhammad.feature.creatorprofile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import com.muhammad.common.theme.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muhammad.core.rippleClickable
import com.muhammad.data.domain.model.Video

@Composable
fun ProfileShortVideoItem(modifier: Modifier, video: Video,onClick : () -> Unit) {
    Box(
        modifier = modifier
            .height(120.dp)
            .border(
                0.1.dp, MaterialTheme.colorScheme.onBackground,
                RoundedCornerShape(0.dp)
            ).rippleClickable{
                onClick()
            }
    ) {
        AsyncImage(
            model = video.thumbnail,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_play_outline),
                contentDescription = null,
                modifier = Modifier.size(10.dp)
            )
            Text(
                text = video.videoStats.formattedViewsCount,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}