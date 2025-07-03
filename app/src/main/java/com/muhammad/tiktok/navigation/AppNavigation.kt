package com.muhammad.tiktok.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhammad.feature.authentication.AuthorizationScreen
import com.muhammad.feature.cameramedia.CameraMediaScreen
import com.muhammad.feature.creatorprofile.CreatorProfileScreen
import com.muhammad.feature.home.HomeScreen
import com.muhammad.feature.home.HomeViewModel
import com.muhammad.feature.inbox.InboxScreen
import com.muhammad.feature.loginwithemailphone.LoginWithEmailPhoneScreen
import com.muhammad.feature.myprofile.MyProfileScreen
import com.muhammad.feature.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(navHostController: NavHostController) {
    val homeViewModel = koinViewModel<HomeViewModel>()
    NavHost(navController = navHostController, startDestination = Destinations.HomeScreen){
        composable<Destinations.HomeScreen>{
            HomeScreen(viewModel = homeViewModel, onUserClick = {userId ->
                navHostController.navigate(Destinations.CreatorProfileScreen(userId))
            })
        }
        composable<Destinations.InboxScreen>{
            InboxScreen(onSignUp = {
                navHostController.navigate(Destinations.AuthorizationScreen)
            })
        }
        composable<Destinations.MyProfileScreen>{
            MyProfileScreen(onSettingClick = {
                navHostController.navigate(Destinations.SettingsScreen)
            }, onSignUpClick = {
                navHostController.navigate(Destinations.AuthorizationScreen)
            })
        }
        composable<Destinations.FriendsScreen>{
            AuthorizationScreen(onBack = {
                navHostController.navigateUp()
            }, onEmailLoginClick = {
                navHostController.navigate(Destinations.LoginEmailPhoneScreen)
            })
        }
        composable<Destinations.SettingsScreen>{
            SettingsScreen(onBack = {
                navHostController.navigateUp()
            }, onAccountClick = {
                navHostController.navigate(Destinations.AuthorizationScreen)
            })
        }
        composable<Destinations.AuthorizationScreen>{
            AuthorizationScreen(onBack = {
                navHostController.navigateUp()
            }, onEmailLoginClick = {
                navHostController.navigate(Destinations.LoginEmailPhoneScreen)
            })
        }
        composable<Destinations.LoginEmailPhoneScreen>{
            LoginWithEmailPhoneScreen(onBack = {
                navHostController.navigateUp()
            })
        }
        composable<Destinations.CameraMediaScreen>{
            CameraMediaScreen(onBack = {
                navHostController.navigateUp()
            })
        }
        composable<Destinations.CreatorProfileScreen>{
            CreatorProfileScreen(onBack = {
                navHostController.navigateUp()
            })
        }
    }
}