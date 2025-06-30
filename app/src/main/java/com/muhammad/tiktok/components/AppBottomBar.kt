package com.muhammad.tiktok.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.muhammad.tiktok.navigation.BottomBarDestination

@Composable
fun AppBottomBar(navHostController: NavHostController) {
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val showBottomBar = currentDestination?.hierarchy?.any {
        it.route in listOf(
            BottomBarDestination.HOME.route::class.qualifiedName,
            BottomBarDestination.FRIENDS.route::class.qualifiedName,
            BottomBarDestination.INBOX.route::class.qualifiedName,
            BottomBarDestination.Profile.route::class.qualifiedName
        )
    } == true
    if (showBottomBar) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 16.dp), containerColor = MaterialTheme.colorScheme.background
        ) {
            BottomBarDestination.entries.forEach { destination ->
                BottomItem(destination, navHostController, currentDestination = currentDestination)
            }
        }
    }
}

@Composable
fun RowScope.BottomItem(
    destination: BottomBarDestination,
    navHostController: NavHostController, currentDestination: NavDestination?,
) {
    val isCurrentDestination = currentDestination?.hierarchy?.any {
        it.route == destination.route::class.qualifiedName
    } == true
    val isAddVideoDestination = destination.route == BottomBarDestination.ADD.route
    val icon = when {
        destination == BottomBarDestination.ADD -> destination.darkModeIcon
            ?: destination.unFilledIcon

        isCurrentDestination -> destination.filledIcon ?: destination.unFilledIcon
        else -> destination.unFilledIcon
    }
    val size = if (destination == BottomBarDestination.ADD) {
        42.dp
    } else 22.dp
    NavigationBarItem(
        label = {
            destination.title?.let { text ->
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    softWrap = false, text = stringResource(text),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (isCurrentDestination) 1f else 0.7f)
                )
            }
        }, icon = {
            val tint = when {
                isCurrentDestination && !isAddVideoDestination-> MaterialTheme.colorScheme.primary
                !isCurrentDestination && !isAddVideoDestination-> MaterialTheme.colorScheme.onBackground
                else -> Color.Unspecified
            }
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null, tint = tint,
                modifier = Modifier
                    .size(size)
            )
        }, colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.background,
        ), selected = isCurrentDestination, onClick = {
            if (!isCurrentDestination) {
                navHostController.navigate(destination.route) {
                    launchSingleTop = true
                }
            }
        })
}