package com.example.projectlpg.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val route: String, val icon: ImageVector) {
    Home("home", Icons.Default.Home),
    Devices("devices", Icons.Default.AddCircle),
    Settings("settings", Icons.Default.Settings);
}