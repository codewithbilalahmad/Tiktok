package com.muhammad.feature.creatorprofile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muhammad.common.theme.R
import com.muhammad.common.ui.AppButton
import com.muhammad.common.ui.AppExpandedText
import com.muhammad.data.domain.model.User

@Composable
fun ProfileDetailSection(
    modifier: Modifier = Modifier,
    user: User,
    onFollowerClick: () -> Unit,
    onVideosClick: () -> Unit,
    onViewsClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 30.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(80.dp)) {
            AsyncImage(
                model = user.profileImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .fillMaxSize()
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.add_circle),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(26.dp)
                    .background(MaterialTheme.colorScheme.onBackground, CircleShape)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.onBackground, CircleShape
                    )
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            if(user.isVerified){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.verified),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProfileStats(
                title = stringResource(R.string.following),
                value = user.formattedFollowersCount, onClick = onFollowerClick
            )
            VerticalDivider(
                thickness = 0.3.dp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.height(20.dp)
            )
            ProfileStats(
                title = stringResource(R.string.videos),
                value = user.videos.toString(), onClick = onVideosClick
            )
            VerticalDivider(
                thickness = 0.3.dp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.height(20.dp)
            )
            ProfileStats(
                title = stringResource(R.string.views),
                value = user.formattedViewCount, onClick = onViewsClick
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
        ) {
            AppButton(
                text = stringResource(R.string.edit_profile),
                contentPadding = PaddingValues(12.dp),
                shape = RoundedCornerShape(6.dp),
            ) {

            }
            AppButton(
                text = stringResource(R.string.share_profile),
                contentPadding = PaddingValues(12.dp),
                shape = RoundedCornerShape(6.dp)
            ) {

            }
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ), shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_user),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        if (user.bio.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            AppExpandedText(
                text = user.bio,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun ProfileStats(title: String, value: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Light)
        )
    }
}