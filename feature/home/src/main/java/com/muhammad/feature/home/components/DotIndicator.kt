package com.muhammad.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun  DotIndicator(modifier: Modifier, dotSize: Int, currentDot: Int) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(dotSize){index ->
            val isCurrentDot = index == currentDot
            val size = if(isCurrentDot) 9.dp else 6.dp
            val color = if(isCurrentDot) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            Box(modifier = Modifier.padding(3.dp).size(size).clip(CircleShape).background(color)){
                if(index != dotSize - 1){
                    Spacer(Modifier.width(2.dp))
                }
            }
        }
    }
}