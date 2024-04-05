package com.example.projectlpg.ui.navigation


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import java.util.Locale

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf( Screen.Devices, Screen.Home,Screen.Settings)
    NavigationBar {
        val currentRoute = navController.currentDestination?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.route) },
                label = {
                    Text(screen.route.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    })
                },
                selected = currentRoute == screen.route,
                onClick = {
                    // Navigate to the clicked screen route
                    navController.navigate(screen.route) {
                        // Avoid multiple copies of the same destination on the stack
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}



