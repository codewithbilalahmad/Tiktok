package com.muhammad.feature.myprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.R
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.ui.AppButton
import com.muhammad.common.ui.AppTopBar

@Composable
fun MyProfileScreen(onSettingClick: () -> Unit, onSignUpClick: () -> Unit) {
    Scaffold(topBar = {
        AppTopBar(title = stringResource(R.string.profile), actions = {
            IconButton(onClick = onSettingClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger),
                    contentDescription = null
                )
            }
        })
    }, modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.sign_up_for_an_account),
                color = SubTextColor
            )
            Spacer(Modifier.height(16.dp))
            AppButton(text= stringResource(R.string.sign_up), modifier = Modifier.fillMaxWidth(0.65f)) {
                onSignUpClick()
            }
        }
    }
}