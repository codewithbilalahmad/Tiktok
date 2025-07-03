package com.muhammad.feature.cameramedia

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammad.core.DisableRippleInteractionSource
import com.muhammad.core.getCurrentBrightness
import com.muhammad.feature.cameramedia.components.CameraScreen
import com.muhammad.feature.cameramedia.components.TemplateTabScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CameraMediaScreen(
    viewModel: CameraMediaViewModel = koinViewModel(),onBack : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tabs = Tabs.entries
    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = (context as Activity)
    val minimumScreenBrightness = 0.25f
    DisposableEffect(Unit) {
        val attr = activity.window.attributes.apply {
            if (context.getCurrentBrightness() < minimumScreenBrightness) {
                screenBrightness = minimumScreenBrightness
            }
        }
        context.window.attributes = attr
        onDispose {
            context.window.attributes = attr.apply {
                screenBrightness = context.getCurrentBrightness()
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        val edgePadding = configuration.screenWidthDp.div(2).dp
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = {},
            indicator = {},
            edgePadding = edgePadding
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = index == pagerState.currentPage
                Tab(
                    selected = isSelected,
                    interactionSource = remember { DisableRippleInteractionSource() },
                    text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val color =
                                if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
                                    0.6f
                                )
                            Text(
                                text = stringResource(tab.title),
                                style = MaterialTheme.typography.labelLarge.copy(color = color)
                            )
                            Box(
                                modifier = Modifier
                                    .alpha(if (isSelected) 1f else 0f)
                                    .padding(top = 10.dp)
                                    .size(5.dp).background(MaterialTheme.colorScheme.onBackground, CircleShape
                            ))
                        }
                    }, onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    })
            }
        }
    }) { paddingValues ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0, 1 -> {
                    CameraScreen(cameraOpenType = tabs[page], onBack = onBack)
                }

                2 -> {
                    TemplateTabScreen(state = state, onBack = onBack)
                }
            }
        }
    }
}