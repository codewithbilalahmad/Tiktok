package com.muhammad.tiktok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.muhammad.common.theme.TiktokTheme
import com.muhammad.tiktok.components.AppBottomBar
import com.muhammad.tiktok.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiktokTheme {
                val navHostController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    AppBottomBar(navHostController = navHostController)
                }) { padding ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)) {
                        AppNavigation(navHostController = navHostController)
                    }
                }
            }
        }
    }
}