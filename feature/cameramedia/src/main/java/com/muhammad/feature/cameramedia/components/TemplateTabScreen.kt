package com.muhammad.feature.cameramedia.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.muhammad.common.theme.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.ui.AppButton
import com.muhammad.common.ui.AppLoading
import com.muhammad.data.domain.model.Template
import com.muhammad.feature.cameramedia.CameraMediaState
import kotlin.math.absoluteValue

@Composable
fun TemplateTabScreen(state: CameraMediaState, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = {
                onBack()
            }, modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 2.dp, start = 6.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(state.isTemplatesLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    AppLoading(modifier = Modifier)
                }
            } else{
                TemplatePager(state = state)
            }
        }
        AppButton(
            text = stringResource(R.string.upload_photos),
            modifier = Modifier.fillMaxWidth(0.65f), shape = CircleShape
        ) {

        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun ColumnScope.TemplatePager(state: CameraMediaState) {
    val templates = state.templates
    if(templates?.isNotEmpty() == true){
        val pagerState = rememberPagerState { templates.size }
        val currentIndex by remember {
            derivedStateOf { pagerState.settledPage }
        }
        Text(
            text = templates[currentIndex].name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = templates[currentIndex].hint,
            style = MaterialTheme.typography.labelLarge.copy(
                color = SubTextColor,
                textAlign = TextAlign.Center
            ), modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(20.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 65.dp)
        ) { page ->
            TemplateCard(page = page, pagerState = pagerState, template = templates[page])
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "${currentIndex + 1}/${templates.size}",
            style = MaterialTheme.typography.labelMedium.copy(color = SubTextColor)
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun TemplateCard(page: Int, pagerState: PagerState, template: Template) {
    val pageOffset =
        ((pagerState.currentPage - page) + (pagerState.currentPageOffsetFraction)).absoluteValue
    Card(modifier = Modifier.graphicsLayer {
        lerp(
            start = 0.82f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }
    }, shape = RoundedCornerShape(16.dp)) {
        val blur = if (pagerState.settledPage == page) 0.dp else 60.dp
        Box(modifier = Modifier.blur(blur)) {
            AsyncImage(
                model = template.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}