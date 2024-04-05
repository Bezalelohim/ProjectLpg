package com.example.projectlpg.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectlpg.ui.home.components.GasCylinderProgressBar
import com.example.projectlpg.ui.home.components.LocationPermissionDialog
import com.example.projectlpg.ui.navigation.BottomNavigationBar

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    Scaffold ()
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            LocationPermissionDialog(onPermissionGranted = {
                // Actions to perform once location permission is granted
            })
            val level by viewModel.microcontrollerData.collectAsState()

            GasCylinderProgressBar(
                progress = level,
                modifier = Modifier
                    .width(100.dp)
                    .height(300.dp)
                    .padding(16.dp)
            )
        }

    }
    }
}