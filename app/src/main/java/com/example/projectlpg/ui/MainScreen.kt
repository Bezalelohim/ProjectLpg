package com.example.projectlpg.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectlpg.ui.devices.DevicesScreen
import com.example.projectlpg.ui.home.HomeScreen
import com.example.projectlpg.ui.home.HomeScreenViewModel
import com.example.projectlpg.ui.navigation.BottomNavigationBar
import com.example.projectlpg.ui.navigation.Screen
import com.example.projectlpg.ui.settings.SettingsScreen
import com.example.projectlpg.ui.settings.SettingsScreenViewModel

@Composable

fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Devices.route) {
                DevicesScreen(viewModel = hiltViewModel())
            }
            composable(Screen.Home.route) {
                HomeScreen(viewModel = hiltViewModel())
            }
            composable(Screen.Settings.route) {
                SettingsScreen( viewModel = SettingsScreenViewModel(),navController = navController)
            }
        }
    }
}

