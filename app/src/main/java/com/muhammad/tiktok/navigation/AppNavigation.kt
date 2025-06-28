package com.muhammad.tiktok.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhammad.feature.home.HomeScreen

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Destinations.HomeScreen){
        composable<Destinations.HomeScreen>{
            HomeScreen(navHostController = navHostController)
        }
    }
}