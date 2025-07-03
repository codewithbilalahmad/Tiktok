package com.muhammad.feature.creatorprofile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.R
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.ui.ContentSearchBar
import com.muhammad.common.ui.TiktokVerticalVideoPager
import com.muhammad.data.domain.model.Video

@Composable
fun ProfileVideosScreen(
    index: Int,
    videos: List<Video>,
    onBack: () -> Unit,
    onUserClick: (String) -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        ContentSearchBar(
            onBackClick = onBack,
            placeholder = stringResource(R.string.find_related_content)
        )
    }, bottomBar = {
        OutlinedTextField(
            state = rememberTextFieldState(),
            placeholder = {
                Text(text = stringResource(R.string.add_comment), color = SubTextColor)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(46.dp),
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = Modifier.padding(end = 12.dp, start = 2.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mention),
                        contentDescription = null
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_emoji),
                        contentDescription = null
                    )
                }
            }
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TiktokVerticalVideoPager(
                videos = videos,
                initialPage = index,
                modifier = Modifier.fillMaxSize(),
                onUserClick = onUserClick,
                onAudioClick = {},
                onCommentClick = {},
                onFavouriteClick = {})
        }
    }
}